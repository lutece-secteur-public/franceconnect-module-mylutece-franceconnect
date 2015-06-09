/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.mylutece.modules.franceconnect.web;

import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.RegisteredClient;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.ServerConfiguration;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.Token;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.UserInfo;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.service.BearerTokenAuthenticator;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.service.FranceConnectService;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.service.TokenService;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.service.UserInfoService;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.service.jwt.TokenValidationException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.log4j.Logger;

import java.io.IOException;

import java.math.BigInteger;

import java.net.URLEncoder;

import java.security.SecureRandom;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * AuthLoginServlet
 */
public class OAuthLoginServlet extends HttpServlet
{
    private static final String BEAN_CLIENT = "mylutece-franceconnect.client";
    private static final String BEAN_SERVER = "mylutece-franceconnect.server";
    private static final String PROPERTY_ERROR_PAGE = "mylutece-franceconnect.error.page";
    private static final long serialVersionUID = 1L;
    private static Logger _logger = Logger.getLogger( Constants.LOGGER_FRANCECONNECT );
    private RegisteredClient _client;
    private ServerConfiguration _server;

    /**
     * {@inheritDoc }
     */
    @Override
    protected void service( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException
    {
        getConfiguration(  );

        String strError = request.getParameter( Constants.PARAMETER_ERROR );
        String strCode = request.getParameter( Constants.PARAMETER_CODE );

        if ( strError != null )
        {
            handleError( request, response, strError );
        }
        else if ( strCode != null )
        {
            handleAuthorizationCodeResponse( request, response );
        }
        else
        {
            handleAuthorizationRequest( request, response );
        }
    }

    /**
     * Get client and server configuration
     */
    private void getConfiguration(  )
    {
        if ( _client == null )
        {
            _client = SpringContextService.getBean( BEAN_CLIENT );
            _logger.info( "OAuth Login Servlet initialization - Client ID : " + _client.getClientId(  ) );
        }

        if ( _server == null )
        {
            _server = SpringContextService.getBean( BEAN_SERVER );
            _logger.info( "OAuth Login Servlet initialization - Server Auth URI : " +
                _server.getAuthorizationEndpointUri(  ) );
        }
    }

    /**
     * Handle an error
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @param strError The Error message
     */
    private void handleError( HttpServletRequest request, HttpServletResponse response, String strError )
    {
        try
        {
            UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) +
                    AppPropertiesService.getProperty( PROPERTY_ERROR_PAGE ) );
            url.addParameter( Constants.PARAMETER_ERROR, strError );
            _logger.info( strError );
            response.sendRedirect( url.getUrl(  ) );
        }
        catch ( IOException ex )
        {
            _logger.error( "Error redirecting to the error page : " + ex.getMessage(  ), ex );
        }
    }

    /**
     * Handle an request that contains an authorization code
     *
     * @param request The HTTP request
     * @param response The HTTP response
     */
    private void handleAuthorizationCodeResponse( HttpServletRequest request, HttpServletResponse response )
    {
        String strCode = request.getParameter( Constants.PARAMETER_CODE );
        _logger.info( "OAuth Authorization code received : " + strCode );

        // Check valid state
        if ( !checkState( request ) )
        {
            handleError( request, response, "Invalid state returned by FranceConnect !" );

            return;
        }

        try
        {
            Token token = getToken( strCode, request.getSession(  ) );

            if ( token != null )
            {
                UserInfo userInfo = getUserInfo( token );

                if ( userInfo != null )
                {
                    FranceConnectService.processAuthentication( request, userInfo, token );
                    FranceConnectService.redirect( request, response );
                }
            }
        }
        catch ( IOException ex )
        {
            String strError = "Error retrieving token : " + ex.getMessage(  );
            _logger.error( strError, ex );
            handleError( request, response, strError );
        }
        catch ( HttpAccessException ex )
        {
            String strError = "Error retrieving token : " + ex.getMessage(  );
            _logger.error( strError, ex );
            handleError( request, response, strError );
        }
        catch ( TokenValidationException ex )
        {
            String strError = "Error retrieving token : " + ex.getMessage(  );
            _logger.error( strError, ex );
            handleError( request, response, strError );
        }
    }

    /**
     * check state returned by FranceConnect to the callback uri
     * @param request The HTTP request
     * @return True if the state is valid
     */
    private boolean checkState( HttpServletRequest request )
    {
        String strState = request.getParameter( Constants.PARAMETER_STATE );
        HttpSession session = request.getSession(  );
        String strStored = getStoredState( session );
        boolean bCheck = ( ( strState == null ) || strState.equals( strStored ) );

        if ( !bCheck )
        {
            _logger.debug( "Bad state returned by server : " + strState + " while expecting : " + strStored );
        }

        return bCheck;
    }

    /**
     * Retieve a token using an authorization code
     * @param strAuthorizationCode The authorization code
     * @param session The HTTP session
     * @return The token
     * @throws IOException if an error occurs
     * @throws HttpAccessException if an error occurs
     * @throws TokenValidationException If the token validation failed
     */
    private Token getToken( String strAuthorizationCode, HttpSession session )
        throws IOException, HttpAccessException, TokenValidationException
    {
        String strRedirectUri = _client.getRedirectUri(  );
        Map<String, String> mapParameters = new ConcurrentHashMap<String, String>(  );
        mapParameters.put( Constants.PARAMETER_GRANT_TYPE, Constants.GRANT_TYPE_CODE );
        mapParameters.put( Constants.PARAMETER_CODE, strAuthorizationCode );
        mapParameters.put( Constants.PARAMETER_CLIENT_ID, _client.getClientId(  ) );
        mapParameters.put( Constants.PARAMETER_CLIENT_SECRET, _client.getClientSecret(  ) );

        if ( strRedirectUri != null )
        {
            mapParameters.put( Constants.PARAMETER_REDIRECT_URI, strRedirectUri );
        }

        HttpAccess httpAccess = new HttpAccess(  );
        String strUrl = _server.getTokenEndpointUri(  );

        _logger.debug( "Posted URL : " + strUrl + "\nParameters :\n" + traceMap( mapParameters ) );

        String strResponse = httpAccess.doPost( strUrl, mapParameters );
        _logger.debug( "FranceConnect response : " + strResponse );

        return TokenService.parse( strResponse, _client, _server, getStoredNonce( session ) );
    }

    /**
     * Get UserInfo using a token
     * @param token The token
     * @return User infos
     */
    private UserInfo getUserInfo( Token token )
    {
        UserInfo userInfo = null;
        HttpAccess httpAccess = new HttpAccess(  );

        String strUrl = _server.getUserInfoUri(  );

        try
        {
            RequestAuthenticator authenticator = new BearerTokenAuthenticator( token.getAccessToken(  ) );
            String strResponse = httpAccess.doGet( strUrl, authenticator, null );
            _logger.debug( "FranceConnect response : " + strResponse );

            userInfo = UserInfoService.parse( strResponse );
        }
        catch ( HttpAccessException ex )
        {
            _logger.error( "OAuth Login Error" + ex.getMessage(  ), ex );
        }
        catch ( IOException ex )
        {
            _logger.error( "OAuth Login Error" + ex.getMessage(  ), ex );
        }

        return userInfo;
    }

    /**
     * Handle an authorization request to obtain an authorization code
     * @param request The HTTP request
     * @param response The HTTP response
     */
    private void handleAuthorizationRequest( HttpServletRequest request, HttpServletResponse response )
    {
        try
        {
            HttpSession session = request.getSession( true );

            UrlItem url = new UrlItem( _server.getAuthorizationEndpointUri(  ) );
            url.addParameter( Constants.PARAMETER_CLIENT_ID, _client.getClientId(  ) );
            url.addParameter( Constants.PARAMETER_CLIENT_SECRET, _client.getClientSecret(  ) );
            url.addParameter( Constants.PARAMETER_RESPONSE_TYPE, Constants.RESPONSE_TYPE_CODE );
            url.addParameter( Constants.PARAMETER_REDIRECT_URI, URLEncoder.encode( _client.getRedirectUri(  ), "UTF-8" ) );
            url.addParameter( Constants.PARAMETER_SCOPE, _client.getScopes(  ) );
            url.addParameter( Constants.PARAMETER_STATE, createState( session ) );
            url.addParameter( Constants.PARAMETER_NONCE, createNonce( session ) );

            String strUrl = url.getUrl(  );
            _logger.debug( "OAuth request : " + strUrl );
            response.sendRedirect( strUrl );
        }
        catch ( IOException ex )
        {
            String strError = "Error retrieving an authorization code : " + ex.getMessage(  );
            _logger.error( strError, ex );
            handleError( request, response, strError );
        }
    }

    /**
     * Create a cryptographically random nonce and store it in the session
     *
     * @param session The session
     * @return The nonce
     */
    private static String createNonce( HttpSession session )
    {
        String nonce = new BigInteger( 50, new SecureRandom(  ) ).toString( 16 );
        session.setAttribute( Constants.NONCE_SESSION_VARIABLE, nonce );

        return nonce;
    }

    /**
     * Get the nonce we stored in the session
     *
     * @param session The session
     * @return The stored nonce
     */
    private static String getStoredNonce( HttpSession session )
    {
        return getStoredSessionString( session, Constants.NONCE_SESSION_VARIABLE );
    }

    /**
     * Create a cryptographically random state and store it in the session
     *
     * @param session The session
     * @return The state
     */
    private static String createState( HttpSession session )
    {
        String strState = new BigInteger( 50, new SecureRandom(  ) ).toString( 16 );
        session.setAttribute( Constants.STATE_SESSION_VARIABLE, strState );

        return strState;
    }

    /**
     * Get the state we stored in the session
     *
     * @param session The session
     * @return The stored state
     */
    private static String getStoredState( HttpSession session )
    {
        return getStoredSessionString( session, Constants.STATE_SESSION_VARIABLE );
    }

    /**
     * Get the named stored session variable as a string. Return null if not
     * found or not a string.
     *
     * @param session The session
     * @param strKey The key
     * @return The session string
     */
    private static String getStoredSessionString( HttpSession session, String strKey )
    {
        Object object = session.getAttribute( strKey );

        if ( ( object != null ) && object instanceof String )
        {
            return object.toString(  );
        }
        else
        {
            return null;
        }
    }

    /**
     * Utils to trace map content
     * @param map The map
     * @return The content
     */
    private String traceMap( Map<String, String> map )
    {
        StringBuilder sbTrace = new StringBuilder(  );

        for ( Entry entry : map.entrySet(  ) )
        {
            sbTrace.append( entry.getKey(  ) ).append( ":[" ).append( entry.getValue(  ) ).append( "]\n" );
        }

        return sbTrace.toString(  );
    }
}
