package com.example.unityjiysiapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.jitsi.meet.sdk.*
import java.net.MalformedURLException
import java.net.URL


class JitsiVideoCallActivity : AppCompatActivity() {

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

    val APP_ID = "vpaas-magic-cookie-6873e7cdc63c4e57a9616b1ef297ca02/"
    var token: String = "eyJraWQiOiJ2cGFhcy1tYWdpYy1jb29raWUtNjg3M2U3Y2RjNjNjNGU1N2E5NjE2YjFlZjI5N2NhMDIvYjMyMDNkLVNBTVBMRV9BUFAiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJqaXRzaSIsImV4cCI6MTYxOTEwODU3NiwibmJmIjoxNjE5MTAxMzcxLCJpc3MiOiJjaGF0Iiwicm9vbSI6IioiLCJzdWIiOiJ2cGFhcy1tYWdpYy1jb29raWUtNjg3M2U3Y2RjNjNjNGU1N2E5NjE2YjFlZjI5N2NhMDIiLCJjb250ZXh0Ijp7ImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOnRydWUsIm91dGJvdW5kLWNhbGwiOnRydWUsInRyYW5zY3JpcHRpb24iOnRydWUsInJlY29yZGluZyI6dHJ1ZX0sInVzZXIiOnsibW9kZXJhdG9yIjp0cnVlLCJuYW1lIjoiYXJpaml0NHJlZGFwcGxlIiwiaWQiOiJhdXRoMHw2MDY0MzgzYTI2NmEzMDAwNmIzZGY5N2QiLCJhdmF0YXIiOiIiLCJlbWFpbCI6ImFyaWppdDRyZWRhcHBsZUBnbWFpbC5jb20ifX19.hmBFMO7xnAY343Tw2qieJTUDK9k01REPGyCNLdpaDjueKEeW3m9WGyp6xCWj-6hCRdM3l8QOiGqCxm8bHtZ76yDxEYP8reykO7sa3eBf8ypG5gytspSAsg3Sv2GOHD68ktIucC4ZdZmFuSodEtrx8S1OWmTGkkFzh-50MjgmsmkmhrhJfczoI0qTMZ2lXumIrQZJIj5fzqOyqPiq-YAan8LtDWxcbLG_OkWKOuHOM_lJlMieGTczhCWPdaM9AXXPcnBDUy7k302roF9w4bZZmuS-VXXLzdPaMnVJQhu5TDk-NFdSJsjlbLFcMxcqrGObYcnHuxhL7XN0T9atMcCXkw"
    var room: String = "SampleAppSkilledProcessesAssureEasily"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jitsi_video_call)

        val serverURL: URL
        serverURL = try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            URL("https://8x8.vc")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }

        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(serverURL)
            // When using JaaS, set the obtained JWT here
            // Different features flags can be set
            //.setFeatureFlag("toolbox.enabled", false)
            //.setFeatureFlag("filmstrip.enabled", false)
            .setWelcomePageEnabled(false)
            .build()
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)

        registerForBroadcastMessages()

        setJitsiCallingPage()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun setJitsiCallingPage() {
        if (APP_ID.isNotEmpty()) {
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            val options = JitsiMeetConferenceOptions.Builder()
                .setToken(token)
                .setRoom(APP_ID + room)
                // Settings for audio and video
                //.setAudioMuted(true)
                //.setVideoMuted(true)
                .build()
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(this, options)
            finish()
        }
    }

    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()
        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.action);
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action);
                ... other events
         */
        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    // Example for handling different JitsiMeetSDK events
    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.type) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> {
                    Log.e(
                        "Conference Joined url: ",
                        "${event.data.get("url")}"
                    )
                    Toast.makeText(this, "On Conference joined", Toast.LENGTH_SHORT).show()

                }

                BroadcastEvent.Type.PARTICIPANT_JOINED -> {
                    Log.e(
                        "Participant joined: ",
                        "${event.data.get("name")}"
                    )
                    Toast.makeText(this, "On PARTICIPANT joined", Toast.LENGTH_SHORT).show()
                }

                BroadcastEvent.Type.CONFERENCE_TERMINATED -> {
                    Log.e("CONFERENCE_TERMINATED", " *--------------------* ")
                    Toast.makeText(this, "On CONFERENCE TERMINATED", Toast.LENGTH_SHORT).show()

                    Toast.makeText(this, "ACTIVITY FINISHED", Toast.LENGTH_SHORT).show()
                    /*val returnIntent = Intent()
                    setResult(Activity.RESULT_OK, returnIntent)*/
                }
            }
        }
    }

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        LocalBroadcastManager.getInstance(org.webrtc.ContextUtils.getApplicationContext())
            .sendBroadcast(hangupBroadcastIntent)
    }
}