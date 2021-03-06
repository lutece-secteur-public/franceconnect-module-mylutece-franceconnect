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
package fr.paris.lutece.plugins.mylutece.modules.franceconnect.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import fr.paris.lutece.plugins.franceconnect.oidc.Token;
import fr.paris.lutece.plugins.franceconnect.oidc.UserInfo;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.authentication.FranceConnectAuthentication;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.authentication.FranceConnectUser;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.web.PortalJspBean;


/**
 * France Connect Service
 */
public final class FranceConnectService
{
    private static final FranceConnectAuthentication _authService = new FranceConnectAuthentication(  );
    private static Logger _logger = Logger.getLogger( "lutece.franceconnect" );

    /**
     * private constructor
     */
    private FranceConnectService(  )
    {
    }

    /**
     * Process the authentication
     *
     * @param request The HTTP request
     * @param userInfo Users Info
     */
    public static void processAuthentication( HttpServletRequest request, UserInfo userInfo, Token token )
    {
        FranceConnectUser user = new FranceConnectUser( userInfo.getSub(  ), _authService );
        user.setUserInfo( LuteceUser.BDATE, userInfo.getBirthDate(  ) );
        user.setUserInfo( LuteceUser.GENDER, userInfo.getGender(  ) );
        user.setUserInfo( LuteceUser.NAME_FAMILY, userInfo.getFamilyName(  ) );
        user.setUserInfo( LuteceUser.NAME_GIVEN, userInfo.getGivenName(  ) );
        user.setUserInfo( LuteceUser.NAME_NICKNAME, userInfo.getNickname(  ) );
        user.setUserInfo( LuteceUser.NAME_MIDDLE, userInfo.getMiddleName(  ) );
        user.setUserInfo( LuteceUser.BUSINESS_INFO_ONLINE_EMAIL, userInfo.getEmail(  ) );
        user.setUserInfo( FranceConnectUser.ADDRESS, userInfo.getAddress(  ) );
        user.setUserInfo( FranceConnectUser.BIRTH_PLACE, userInfo.getBirthPlace(  ) );
        user.setUserInfo( FranceConnectUser.BIRTH_COUNTRY, userInfo.getBirthCountry(  ) );
        user.setUserInfo( FranceConnectUser.BIRTH_DATE, userInfo.getBirthDate(  ) );
        user.setUserInfo( FranceConnectUser.ACCESS_TOKEN, token.getAccessToken(  ) );
        user.setUserInfo( FranceConnectUser.IDP_ID, token.getIdToken().getIdProvider() );
        user.setUserInfo( FranceConnectUser.IDP_ACR, token.getIdToken().getAcr() );

        user.setEmail( userInfo.getEmail(  ) );
        user.setBirthPlace( userInfo.getBirthPlace(  ) );
        user.setBirthCountry( userInfo.getBirthCountry(  ) );

        SecurityService.getInstance(  ).registerUser( request, user );
    }

    /**
     * Process the logout
     *
     * @param request The HTTP request
     */
    public static void processLogout( HttpServletRequest request )
    {
        _logger.debug( "Process logout" );
        SecurityService.getInstance(  ).logoutUser( request );
    }

    /**
     * redirect after login or logout
     * @param request The HTTP request
     * @param response The HTTP response
     * @throws IOException if an error occurs
     */
    public static void redirect( HttpServletRequest request, HttpServletResponse response )
        throws IOException
    {
        String strNextURL = PortalJspBean.getLoginNextUrl( request );
        _logger.info( "Next URL : " + strNextURL );

        if ( strNextURL == null )
        {
            strNextURL = SecurityService.getInstance(  ).getLoginPageUrl(  );
        }

        response.sendRedirect( strNextURL );
    }
}
