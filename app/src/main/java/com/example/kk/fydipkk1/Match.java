package com.example.kk.fydipkk1;

/**
 * Created by user on 05-07-2015.
 */
public class Match {

    private int _id;
    private String _username;
    private String _player1;
    private String _player2;
    private String _player11;
    private String _player22;
    private int _points1;
    private int _points2;
    private String mode;

    public Match(String _player1, String _player2, String _username) {
        this._player1 = _player1;
        this._player2 = _player2;
        this._username = _username;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_player1() {
        return _player1;
    }

    public void set_player1(String _player1) {
        this._player1 = _player1;
    }

    public String get_player2() {
        return _player2;
    }

    public void set_player2(String _player2) {
        this._player2 = _player2;
    }

    public int get_points1() {
        return _points1;
    }

    public void set_points1(int _points1) {
        this._points1 = _points1;
    }

    public int get_points2() {
        return _points2;
    }

    public void set_points2(int _points2) {
        this._points2 = _points2;
    }

    public String get_mode() {
        return mode;
    }

    public void set_mode(String mode) {
        this.mode = mode;
    }

    public String get_player11() {
        return _player11;
    }

    public void set_player11(String _player11) {
        this._player11 = _player11;
    }

    public String get_player22() {
        return _player22;
    }

    public void set_player22(String _player22) {
        this._player22 = _player22;
    }
}
