package com.anadi.weatherinfo.ui

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.jvm.Throws

internal class UnlockScreenRule<T : AppCompatActivity>(var activityRule: ActivityScenarioRule<T>) : TestRule {
    override fun apply(statement: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                activityRule.scenario.onActivity {
                    @Suppress("DEPRECATION")
                    it.window.addFlags(
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
                }
                statement.evaluate()
            }
        }
    }

}