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
package fr.paris.lutece.plugins.mylutece.modules.franceconnect.authentication;


/**
 * France Connect AuthResponse
 */
public class FranceConnectAuthValidation
{
    public static final String STATUS_OK = "okay";
    public static final String STATUS_FAILURE = "failure";

    // Variables declarations 
    private String _strStatus;
    private String _strEmail;
    private String _strAudience;
    private int _nExpires;
    private String _strIssuer;
    private String _strReason;

    /**
     * Returns the Status
     *
     * @return The Status
     */
    public String getStatus(  )
    {
        return _strStatus;
    }

    /**
     * Sets the Status
     *
     * @param strStatus The Status
     */
    public void setStatus( String strStatus )
    {
        _strStatus = strStatus;
    }

    /**
     * Returns the Email
     *
     * @return The Email
     */
    public String getEmail(  )
    {
        return _strEmail;
    }

    /**
     * Sets the Email
     *
     * @param strEmail The Email
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }

    /**
     * Returns the Audience
     *
     * @return The Audience
     */
    public String getAudience(  )
    {
        return _strAudience;
    }

    /**
     * Sets the Audience
     *
     * @param strAudience The Audience
     */
    public void setAudience( String strAudience )
    {
        _strAudience = strAudience;
    }

    /**
     * Returns the Expires
     *
     * @return The Expires
     */
    public int getExpires(  )
    {
        return _nExpires;
    }

    /**
     * Sets the Expires
     *
     * @param nExpires The Expires
     */
    public void setExpires( int nExpires )
    {
        _nExpires = nExpires;
    }

    /**
     * Returns the Issuer
     *
     * @return The Issuer
     */
    public String getIssuer(  )
    {
        return _strIssuer;
    }

    /**
     * Sets the Issuer
     *
     * @param strIssuer The Issuer
     */
    public void setIssuer( String strIssuer )
    {
        _strIssuer = strIssuer;
    }

    /**
     * Returns the Reason
     *
     * @return The Reason
     */
    public String getReason(  )
    {
        return _strReason;
    }

    /**
     * Sets the Reason
     *
     * @param strReason The Reason
     */
    public void setReason( String strReason )
    {
        _strReason = strReason;
    }

    /**
     * Returns the authetication status
     * @return true if Okay otherwise false
     */
    public boolean isOK(  )
    {
        return _strStatus.equalsIgnoreCase( STATUS_OK );
    }
}
