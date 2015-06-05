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
package fr.paris.lutece.plugins.mylutece.modules.franceconnect.service.jwt;

import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.RegisteredClient;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.ServerConfiguration;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2.Token;
import fr.paris.lutece.plugins.mylutece.modules.franceconnect.web.Constants;

import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pierre
 */
public class JjwtJWTParserTest
{
    /**
     * Test of parseJWT method, of class JjwtJWTParser.
     */
    @Test
    public void testParseJWT(  ) throws Exception
    {
        System.out.println( "parseJWT" );

        Token token = new Token(  );
        token.setIdTokenString( 
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vZmNwLmludGVnMDEuZGV2LWZyYW5jZWNvbm5lY3QuZnIiLCJzdWIiOiI5YzA4YjBkYTQ0ZTc3NDdhMzQ3Y2E4ZjljM2JmNzFmOTIzNzBhNWEwY2NiOGQ0YzEiLCJhdWQiOiJhOWEyNTg5NWY5ZDc2ZjZjODlhYTIxODMwNTc1YmYzNGIzZjRmNjg0YTcyYTg0YzEzYWIxYzM4MTA2NDNkODU5IiwiZXhwIjoxNDMzNTQzMDczLCJpYXQiOjE0MzM1Mzk0NzMsIm5vbmNlIjoiMTYxZDU3NTYwODk0NiIsImlkcCI6ImRnZmlwIiwiYWNyIjoiZWlkYXMyIn0.Sbd74RBtxmGO6S64Toj5RooEjHJsfuPaFwufLdQUevE" );

        RegisteredClient clientConfig = new RegisteredClient(  );
        clientConfig.setClientSecret( "7504f9f0ef08473a4c26873e9c1b898e567a39e6b76b7e60e93a0cb25cae5eb8" );

        ServerConfiguration serverConfig = null;
        String strStoredNonce = "";
        Logger logger = Logger.getLogger( Constants.LOGGER_FRANCECONNECT );
        JjwtJWTParser instance = new JjwtJWTParser(  );
        instance.parseJWT( token, clientConfig, serverConfig, strStoredNonce, logger );

        System.out.print( token.getIdToken(  ) );
    }
}
