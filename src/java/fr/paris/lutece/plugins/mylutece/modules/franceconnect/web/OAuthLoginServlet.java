/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;

import java.math.BigInteger;

import java.security.SecureRandom;

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
    private static final String RESPONSE_TYPE_CODE = "code";
    private static final String PARAMETER_CODE = "code";
    private static final String PARAMETER_ERROR = "error";
    private static final String PARAMETER_SCOPE = "scope";
    private static final String PARAMETER_STATE = "state";
    private static final String PARAMETER_NONCE = "nonce";
    private static final String PROPERTY_STATE = "mylutece-franceconnect.request.state";
    private static final String PROPERTY_NONCE = "mylutece-franceconnect.request.nonce";
    private static final String PROPERTY_ERROR_PAGE = "mylutece-franceconnect.error.page";
    private final static String REDIRECT_URI_SESION_VARIABLE = "redirect_uri";
    private final static String STATE_SESSION_VARIABLE = "state";
    private final static String NONCE_SESSION_VARIABLE = "nonce";
    private final static String ISSUER_SESSION_VARIABLE = "issuer";
    private static final String TARGET_SESSION_VARIABLE = "target";
    private static final long serialVersionUID = 1L;
    private RegisteredClient _client;
    private ServerConfiguration _server;

    @Override
    protected void service( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException
    {
        if ( _client == null )
        {
            _client = SpringContextService.getBean( BEAN_CLIENT );
            AppLogService.info( "OAuth Login Servlet initialization - Client ID : " + _client.getClientId(  ) );
        }

        if ( _server == null )
        {
            _server = SpringContextService.getBean( BEAN_SERVER );
            AppLogService.info( "OAuth Login Servlet initialization - Server Auth URI : " +
                _server.getAuthorizationEndpointUri(  ) );
        }

        String strError = request.getParameter( PARAMETER_ERROR );
        String strCode = request.getParameter( PARAMETER_CODE );

        if ( strError != null )
        {
            handleError( request, response );
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

    private void handleError( HttpServletRequest request, HttpServletResponse response )
    {
        try
        {
            String strError = request.getParameter( PARAMETER_ERROR );
            UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) +
                    AppPropertiesService.getProperty( PROPERTY_ERROR_PAGE ) );
            url.addParameter( PARAMETER_ERROR, strError );
            AppLogService.info( "OAuth Server Authentication Error : " + strError );
            response.sendRedirect( url.getUrl(  ) );
        }
        catch ( IOException ex )
        {
            AppLogService.error( "OAuth Login Error" + ex.getMessage(  ), ex );
        }
    }

    private void handleAuthorizationCodeResponse( HttpServletRequest request, HttpServletResponse response )
    {
        String strCode = request.getParameter( PARAMETER_CODE );
        AppLogService.info( "OAuth Autorization code received : " + strCode );
        
    }

    private void handleAuthorizationRequest( HttpServletRequest request, HttpServletResponse response )
    {
        try
        {
            //            String strState = AppPropertiesService.getProperty( PROPERTY_STATE );
            //            String strNonce = AppPropertiesService.getProperty( PROPERTY_NONCE );
            HttpSession session = request.getSession( true );

            OAuthClientRequest oauthRequest = OAuthClientRequest.authorizationLocation( _server.getAuthorizationEndpointUri(  ) )
                                                                .setClientId( _client.getClientId(  ) )
                                                                .setRedirectURI( _client.getRedirectUri() )
                                                                .setResponseType( RESPONSE_TYPE_CODE )
                                                                .buildQueryMessage(  );

            UrlItem url = new UrlItem( oauthRequest.getLocationUri(  ) );
            url.addParameter( PARAMETER_SCOPE, _client.getScopes(  ) );
            url.addParameter( PARAMETER_STATE, createState( session ) );
            url.addParameter( PARAMETER_NONCE, createNonce( session ) );
            AppLogService.info( "OAuth request : " + url.getUrl(  ) );
            response.sendRedirect( url.getUrl(  ) );
        }
        catch ( OAuthSystemException ex )
        {
            AppLogService.error( "OAuth Login Error" + ex.getMessage(  ), ex );
        }
        catch ( IOException ex )
        {
            AppLogService.error( "OAuth Login Error" + ex.getMessage(  ), ex );
        }
    }

    /**
     * Create a cryptographically random nonce and store it in the session
     * @param session
     * @return
     */
    private static String createNonce( HttpSession session )
    {
        String nonce = new BigInteger( 50, new SecureRandom(  ) ).toString( 16 );
        session.setAttribute( NONCE_SESSION_VARIABLE, nonce );

        return nonce;
    }

    /**
     * Get the nonce we stored in the session
     * @param session
     * @return
     */
    private static String getStoredNonce( HttpSession session )
    {
        return getStoredSessionString( session, NONCE_SESSION_VARIABLE );
    }

    /**
     * Create a cryptographically random state and store it in the session
     * @param session The session
     * @return
     */
    private static String createState( HttpSession session )
    {
        String strState = new BigInteger( 50, new SecureRandom(  ) ).toString( 16 );
        session.setAttribute( STATE_SESSION_VARIABLE, strState );

        return strState;
    }

    /**
     * Get the state we stored in the session
     * @param session The session
     * @return
     */
    private static String getStoredState( HttpSession session )
    {
        return getStoredSessionString( session, STATE_SESSION_VARIABLE );
    }

    /**
    * Get the named stored session variable as a string. Return null if not found or not a string.
    * @param session The session
    * @param strKey The key
    * @return
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
}
