/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.simwurbs.runnerz.user;



/**
 *
 * @author sumse
 */
public record User(
    Integer id,
    String name,
    String username,
    String email,
    Address address,
    String phone,
    String website,
    Company company
) {

}
