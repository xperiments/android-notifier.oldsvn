/*
 * Copyright 2010 Rodrigo Damazio <rodrigo@damazio.org>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.damazio.notifier.service;

import org.damazio.notifier.notification.Notification;
import org.damazio.notifier.notification.NotificationType;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Listener which can detect and notify when the phone rings.
 *
 * @author rdamazio
 */
class PhoneRingListener extends PhoneStateListener {
  private final NotificationService service;
  private CallerId callerId;

  public PhoneRingListener(NotificationService context) {
    this.service = context;
    this.callerId = CallerId.create(context);
  }

  @Override
  public void onCallStateChanged(int state, String incomingNumber) {
    if (state == TelephonyManager.CALL_STATE_RINGING) {
      String notificationContents = callerId.buildCallerIdString(incomingNumber);
      Notification notification =
          new Notification(service, NotificationType.RING, incomingNumber, notificationContents);
      service.sendNotification(notification);
    }
  }
}