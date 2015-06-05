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
package fr.paris.lutece.plugins.mylutece.modules.franceconnect.oauth2;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * UserInfo
 */
public class UserInfo
{
    @JsonProperty( "sub" )
    private String _strSub;
    @JsonProperty( "gender" )
    private String _strGender;
    @JsonProperty( "name" )
    private String _strName;
    @JsonProperty( "preferred_username" )
    private String _strPreferredUsername;
    @JsonProperty( "given_name" )
    private String _strGivenName;
    @JsonProperty( "family_name" )
    private String _strFamilyName;
    @JsonProperty( "middle_name" )
    private String _strMiddleName;
    @JsonProperty( "nickname" )
    private String _strNickname;
    @JsonProperty( "profile" )
    private String _strProfile;
    @JsonProperty( "picture" )
    private String _strPicture;
    @JsonProperty( "website" )
    private String _strWebsite;
    @JsonProperty( "zone_info" )
    private String _strZoneInfo;
    @JsonProperty( "locale" )
    private String _strLocale;
    @JsonProperty( "updated_time" )
    private String _strUpdatedTime;
    @JsonProperty( "birthdate" )
    private String _strBirthDate;
    @JsonProperty( "birthcountry" )
    private String _strBirthCountry;
    @JsonProperty( "birthplace" )
    private String _strBirthPlace;
    @JsonProperty( "email" )
    private String _strEmail;
    @JsonProperty( "email_verified" )
    private String _strEmailVerified;
    @JsonProperty( "phone_number" )
    private String _strPhoneNumber;
    @JsonProperty( "phone_number_verified" )
    private String _strPhoneNumberVerified;
    @JsonProperty( "address" )
    private String _strAddress;

    /**
     * Returns the Sub
     * @return The Sub
     */
    public String getSub(  )
    {
        return _strSub;
    }

    /**
     * Sets the Sub
     * @param strSub The Sub
     */
    public void setSub( String strSub )
    {
        _strSub = strSub;
    }

    /**
     * Returns the Gender
     * @return The Gender
     */
    public String getGender(  )
    {
        return _strGender;
    }

    /**
     * Sets the Gender
     * @param strGender The Gender
     */
    public void setGender( String strGender )
    {
        _strGender = strGender;
    }

    /**
     * Returns the Name
     * @return The Name
     */
    public String getName(  )
    {
        return _strName;
    }

    /**
     * Sets the Name
     * @param strName The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the PreferredUsername
     * @return The PreferredUsername
     */
    public String getPreferredUsername(  )
    {
        return _strPreferredUsername;
    }

    /**
     * Sets the PreferredUsername
     * @param strPreferredUsername The PreferredUsername
     */
    public void setPreferredUsername( String strPreferredUsername )
    {
        _strPreferredUsername = strPreferredUsername;
    }

    /**
     * Returns the GivenName
     * @return The GivenName
     */
    public String getGivenName(  )
    {
        return _strGivenName;
    }

    /**
     * Sets the GivenName
     * @param strGivenName         The GivenName
     */
    public void setGivenName( String strGivenName )
    {
        _strGivenName = strGivenName;
    }

    /**
     * Returns the FamilyName
     * @return The FamilyName
     */
    public String getFamilyName(  )
    {
        return _strFamilyName;
    }

    /**
     * Sets the FamilyName
     * @param strFamilyName The FamilyName
     */
    public void setFamilyName( String strFamilyName )
    {
        _strFamilyName = strFamilyName;
    }

    /**
     * Returns the MiddleName
     * @return The MiddleName
     */
    public String getMiddleName(  )
    {
        return _strMiddleName;
    }

    /**
     * Sets the MiddleName
     * @param strMiddleName The MiddleName
     */
    public void setMiddleName( String strMiddleName )
    {
        _strMiddleName = strMiddleName;
    }

    /**
     * Returns the Nickname
     * @return The Nickname
     */
    public String getNickname(  )
    {
        return _strNickname;
    }

    /**
     * Sets the Nickname
     * @param strNickname         The Nickname
     */
    public void setNickname( String strNickname )
    {
        _strNickname = strNickname;
    }

    /**
     * Returns the Profile
     * @return The Profile
     */
    public String getProfile(  )
    {
        return _strProfile;
    }

    /**
     * Sets the Profile
     * @param strProfile         The Profile
     */
    public void setProfile( String strProfile )
    {
        _strProfile = strProfile;
    }

    /**
     * Returns the Picture
     * @return The Picture
     */
    public String getPicture(  )
    {
        return _strPicture;
    }

    /**
     * Sets the Picture
     * @param strPicture         The Picture
     */
    public void setPicture( String strPicture )
    {
        _strPicture = strPicture;
    }

    /**
     * Returns the Website
     * @return The Website
     */
    public String getWebsite(  )
    {
        return _strWebsite;
    }

    /**
     * Sets the Website
     * @param strWebsite         The Website
     */
    public void setWebsite( String strWebsite )
    {
        _strWebsite = strWebsite;
    }

    /**
     * Returns the ZoneInfo
     * @return The ZoneInfo
     */
    public String getZoneInfo(  )
    {
        return _strZoneInfo;
    }

    /**
     * Sets the ZoneInfo
     * @param strZoneInfo The ZoneInfo
     */
    public void setZoneInfo( String strZoneInfo )
    {
        _strZoneInfo = strZoneInfo;
    }

    /**
     * Returns the Locale
     * @return The Locale
     */
    public String getLocale(  )
    {
        return _strLocale;
    }

    /**
     * Sets the Locale
     * @param strLocale         The Locale
     */
    public void setLocale( String strLocale )
    {
        _strLocale = strLocale;
    }

    /**
     * Returns the UpdatedTime
     * @return The UpdatedTime
     */
    public String getUpdatedTime(  )
    {
        return _strUpdatedTime;
    }

    /**
     * Sets the UpdatedTime
     * @param strUpdatedTime         The UpdatedTime
     */
    public void setUpdatedTime( String strUpdatedTime )
    {
        _strUpdatedTime = strUpdatedTime;
    }

    /**
     * Returns the BirthDate
     * @return The BirthDate
     */
    public String getBirthDate(  )
    {
        return _strBirthDate;
    }

    /**
     * Sets the BirthDate
     * @param strBirthDate The BirthDate
     */
    public void setBirthDate( String strBirthDate )
    {
        _strBirthDate = strBirthDate;
    }

    /**
     * Returns the BirthCountry
     * @return The BirthCountry
     */
    public String getBirthCountry(  )
    {
        return _strBirthCountry;
    }

    /**
     * Sets the BirthCountry
     * @param strBirthCountry The BirthCountry
     */
    public void setBirthCountry( String strBirthCountry )
    {
        _strBirthCountry = strBirthCountry;
    }

    /**
     * Returns the BirthPlace
     * @return The BirthPlace
     */
    public String getBirthPlace(  )
    {
        return _strBirthPlace;
    }

    /**
     * Sets the BirthPlace
     * @param strBirthPlace The BirthPlace
     */
    public void setBirthPlace( String strBirthPlace )
    {
        _strBirthPlace = strBirthPlace;
    }

    /**
     * Returns the Email
     * @return The Email
     */
    public String getEmail(  )
    {
        return _strEmail;
    }

    /**
     * Sets the Email
     * @param strEmail         The Email
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }

    /**
     * Returns the EmailVerified
     * @return The EmailVerified
     */
    public String getEmailVerified(  )
    {
        return _strEmailVerified;
    }

    /**
     * Sets the EmailVerified
     * @param strEmailVerified         The EmailVerified
     */
    public void setEmailVerified( String strEmailVerified )
    {
        _strEmailVerified = strEmailVerified;
    }

    /**
     * Returns the PhoneNumber
     * @return The PhoneNumber
     */
    public String getPhoneNumber(  )
    {
        return _strPhoneNumber;
    }

    /**
     * Sets the PhoneNumber
     * @param strPhoneNumber         The PhoneNumber
     */
    public void setPhoneNumber( String strPhoneNumber )
    {
        _strPhoneNumber = strPhoneNumber;
    }

    /**
     * Returns the PhoneNumberVerified
     * @return The PhoneNumberVerified
     */
    public String getPhoneNumberVerified(  )
    {
        return _strPhoneNumberVerified;
    }

    /**
     * Sets the PhoneNumberVerified
     * @param strPhoneNumberVerified The PhoneNumberVerified
     */
    public void setPhoneNumberVerified( String strPhoneNumberVerified )
    {
        _strPhoneNumberVerified = strPhoneNumberVerified;
    }

    /**
     * Returns the Address
     * @return The Address
     */
    public String getAddress(  )
    {
        return _strAddress;
    }

    /**
     * Sets the Address
     * @param strAddress The Address
     */
    public void setAddress( String strAddress )
    {
        _strAddress = strAddress;
    }
}
