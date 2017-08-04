/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.main;

import java.io.IOException;

/**
 *
 * @author TruongDV
 */
public class Main {

    private static ServerEndPoint serverEndPoint;

    public static void main(String[] args) {
        serverEndPoint = new ServerEndPoint(8000);     
        try {
            serverEndPoint.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
