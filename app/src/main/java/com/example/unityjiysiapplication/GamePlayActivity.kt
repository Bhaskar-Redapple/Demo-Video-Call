package com.example.unityjiysiapplication

import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

class GamePlayActivity : UnityPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)


        mUnityPlayer = UnityPlayer(this)

        //setContentView(mUnityPlayer)

        mUnityPlayer = UnityPlayer(this)
        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        findViewById<FrameLayout>(R.id.main_layout).addView(mUnityPlayer.view, 0, lp)

       /* BridgeClass.instance()?.setContext(this)
        BridgeClass.instance()?.setActivity(this)
        BridgeClass.instance()?.getDataFromApp(jsonStringData)*/
    }

    override fun onDestroy() {
        mUnityPlayer!!.quit()
        super.onDestroy()
    }


    override fun onPause() {
        super.onPause()
        mUnityPlayer!!.pause()
    }

    override fun onResume() {
        super.onResume()
        mUnityPlayer!!.resume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        mUnityPlayer!!.windowFocusChanged(hasFocus)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mUnityPlayer.configurationChanged(newConfig)
    }
}