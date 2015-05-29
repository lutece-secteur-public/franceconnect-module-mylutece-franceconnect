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
package fr.paris.lutece.plugins.mylutece.modules.franceconnect.service;

import fr.paris.lutece.plugins.mylutece.modules.franceconnect.authentication.FranceConnectAuthValidation;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


/**
 * France Connect Utils
 */
public final class FranceConnectUtils
{
    private static final String STATUS = "status";
    private static final String REASON = "reason";
    private static final String EMAIL = "email";
    private static final String AUDIENCE = "audience";
    private static final String ISSUER = "issuer";
    private static final String EXPIRES = "expires";

    /** Private constructor */
    private FranceConnectUtils(  )
    {
    }

    /**
     * Parse the authentication response
     * @param strJSON The response as JSON format
     * @return A an authentication validation object
     */
    public static FranceConnectAuthValidation parseResponse( String strJSON )
    {
        JSONObject jsonResponse = (JSONObject) JSONSerializer.toJSON( strJSON );
        FranceConnectAuthValidation response = new FranceConnectAuthValidation(  );
        response.setStatus( jsonResponse.getString( STATUS ) );

        if ( response.getStatus(  ).equalsIgnoreCase( FranceConnectAuthValidation.STATUS_OK ) )
        {
            response.setEmail( jsonResponse.getString( EMAIL ) );
            response.setAudience( jsonResponse.getString( AUDIENCE ) );
            response.setIssuer( jsonResponse.getString( ISSUER ) );
            response.setExpires( jsonResponse.getInt( EXPIRES ) );
        }
        else
        {
            response.setReason( jsonResponse.getString( REASON ) );
        }

        return response;
    }
}
