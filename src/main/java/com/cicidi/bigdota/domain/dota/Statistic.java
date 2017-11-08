package com.cicidi.bigdota.domain.dota;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Statistic {
    private List<String> team_0_players;
    private List<String> team_1_players;

    private List<String> team_0_heros;
    private List<String> team_1_heros;

    private String gameMode;

    private boolean team_0_win;

    public Statistic() {
    }

    public Statistic(List<String> team_0_players, List<String> team_1_players, List<String> team_0_heros, List<String> team_1_heros, boolean team_0_win) {
        this.team_0_players = team_0_players;
        this.team_1_players = team_1_players;
        this.team_0_heros = team_0_heros;
        this.team_1_heros = team_1_heros;
        this.team_0_win = team_0_win;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("team_0_players", team_0_players)
                .append("team_1_players", team_1_players)
                .append("team_0_heros", team_0_heros)
                .append("team_1_heros", team_1_heros)
                .append("team_0_win", team_0_win)
                .toString();
    }

    public List<String> getTeam_0_players() {
        return team_0_players;
    }

    public void setTeam_0_players(List<String> team_0_players) {
        this.team_0_players = team_0_players;
    }

    public List<String> getTeam_1_players() {
        return team_1_players;
    }

    public void setTeam_1_players(List<String> team_1_players) {
        this.team_1_players = team_1_players;
    }

    public List<String> getTeam_0_heros() {
        return team_0_heros;
    }

    public void setTeam_0_heros(List<String> team_0_heros) {
        this.team_0_heros = team_0_heros;
    }

    public List<String> getTeam_1_heros() {
        return team_1_heros;
    }

    public void setTeam_1_heros(List<String> team_1_heros) {
        this.team_1_heros = team_1_heros;
    }

    public boolean isTeam_0_win() {
        return team_0_win;
    }

    public void setTeam_0_win(boolean team_0_win) {
        this.team_0_win = team_0_win;
    }
}
