package com.cicidi.bigdota.domain.dota;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by cicidi on 10/16/2017.
 */
@Table(value = "dota_player")
public final class DotaPlayer {
    @PrimaryKey
    private String account_id;
    @Column
    private String steamid;
    @Column
    private String avatar;
    @Column
    private String avatarmedium;
    @Column
    private String avatarfull;
    @Column
    private String profileurl;
    @Column
    private String personaname;
    @Column
    private String last_login;
    @Column
    private String full_history_time;
    @Column
    private String cheese;
    @Column
    private String fh_unavailable;
    @Column
    private String loccountrycode;
    @Column
    private String last_match_time;
    @Column
    private String name;
    @Column
    private String country_code;
    @Column
    private int fantasy_role;
    @Column
    private String team_id;
    @Column
    private String team_name;
    @Column
    private String team_tag;
    @Column
    private boolean is_locked;
    @Column
    private boolean is_pro;
    @Column
    private long locked_until;
    @Column(value = "match_list")
    private String matchList;

    public DotaPlayer() {
    }

    public DotaPlayer(String accountId, String str) {
        this.account_id = accountId;
        this.matchList = str;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarmedium() {
        return avatarmedium;
    }

    public void setAvatarmedium(String avatarmedium) {
        this.avatarmedium = avatarmedium;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getFull_history_time() {
        return full_history_time;
    }

    public void setFull_history_time(String full_history_time) {
        this.full_history_time = full_history_time;
    }

    public String getCheese() {
        return cheese;
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public String getFh_unavailable() {
        return fh_unavailable;
    }

    public void setFh_unavailable(String fh_unavailable) {
        this.fh_unavailable = fh_unavailable;
    }

    public String getLoccountrycode() {
        return loccountrycode;
    }

    public void setLoccountrycode(String loccountrycode) {
        this.loccountrycode = loccountrycode;
    }

    public String getLast_match_time() {
        return last_match_time;
    }

    public void setLast_match_time(String last_match_time) {
        this.last_match_time = last_match_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public int getFantasy_role() {
        return fantasy_role;
    }

    public void setFantasy_role(int fantasy_role) {
        this.fantasy_role = fantasy_role;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_tag() {
        return team_tag;
    }

    public void setTeam_tag(String team_tag) {
        this.team_tag = team_tag;
    }

    public boolean is_locked() {
        return is_locked;
    }

    public void setIs_locked(boolean is_locked) {
        this.is_locked = is_locked;
    }

    public boolean is_pro() {
        return is_pro;
    }

    public void setIs_pro(boolean is_pro) {
        this.is_pro = is_pro;
    }

    public long getLocked_until() {
        return locked_until;
    }

    public void setLocked_until(long locked_until) {
        this.locked_until = locked_until;
    }

    public boolean isIs_locked() {
        return is_locked;
    }

    public boolean isIs_pro() {
        return is_pro;
    }

    public String getMatchList() {
        return matchList;
    }

    public void setMatchList(String matchList) {
        this.matchList = matchList;
    }
}