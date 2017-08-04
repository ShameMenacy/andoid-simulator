/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.main;

import vn.ugame.message.Message;

/**
 *
 * @author TruongDV
 */
public interface MessageListener {
    void onMessage(ClientSocketHandler handler,Message msg);
}
