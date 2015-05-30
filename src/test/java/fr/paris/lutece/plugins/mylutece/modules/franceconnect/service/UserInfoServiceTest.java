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

import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.UserInfo;
import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;


/**
 * UserInfoService Test
 */
public class UserInfoServiceTest
{
    private static final String JSON = "{\"sub\": \"012632343621c2600f43b5ab1936743dfc8a199ceca851a7\",\"birthcountry\": \"33\", \"birthplace\": \"91272\",\"birthdate\": \"1976-02-24\",\"given_name\": \"Pierre\",\"family_name\": \"Dupond\", \"gender\": \"male\",\"preferred_username\": \"Dupont\" }";

    /**
     * Test of parse method, of class UserInfoService.
     * @throws java.io.IOException
     */
    @Test
    public void testParse(  ) throws IOException
    {
        System.out.println( "parse" );

        String strJson = JSON;
        UserInfo userInfo = UserInfoService.parse( strJson );

        assertEquals( userInfo.getGivenName(), "Pierre" );
        assertEquals( userInfo.getFamilyName(), "Dupond" );
    }
}

