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

import fr.paris.lutece.plugins.mylutece.web.MyLuteceApp;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.PortalJspBean;
import fr.paris.lutece.portal.web.xpages.XPage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * France Connect XPage Application
 */
@Controller( xpageName = "franceconnect", pagePathI18nKey = "module.mylutece.franceconnect.loginPagePath", pageTitleI18nKey = "module.mylutece.franceconnect.loginPageTitle" )
public class FranceConnectApp extends MVCApplication
{
    // Views
    private static final String VIEW_HOME = "home";

    // Templates
    private static final String TEMPLATE_LOGIN_PAGE = "skin/plugins/mylutece/modules/franceconnect/login_form.html";

    // Markers
    private static final String MARK_USER = "user";
    private static final String MARK_URL_DOLOGIN = "url_dologin";
    private static final String MARK_URL_DOLOGOUT = "url_dologout";
    private static final String MARK_NEXT_URL = "next_url";
    private static final long serialVersionUID = 1L;

    /**
     * Build the Login page
     * @param page The XPage object to fill
     * @param request The HTTP request
     * @param locale The current locale
     * @return The XPage object containing the page content
     */
    @View( value = VIEW_HOME, defaultView = true )
    public XPage getHomePage( HttpServletRequest request )
    {
        Map<String, Object> model = getModel(  );

        String strError = request.getParameter( Constants.PARAMETER_ERROR );

        if ( strError != null )
        {
            addError( strError );
        }

        LuteceUser user = SecurityService.getInstance(  ).getRegisteredUser( request );

        model.put( MARK_USER, user );
        model.put( MARK_URL_DOLOGIN, MyLuteceApp.getDoLoginUrl(  ) );
        model.put( MARK_URL_DOLOGOUT, MyLuteceApp.getDoLogoutUrl(  ) );

        return getXPage( TEMPLATE_LOGIN_PAGE, request.getLocale(  ), model );
    }
}
