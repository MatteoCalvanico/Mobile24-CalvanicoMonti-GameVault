package it.unibo.gamevault.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import it.unibo.gamevault.R

class GameActivity : AppCompatActivity() {

    private lateinit var gameTitle: TextView
    private lateinit var gameAbout: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameTitle = findViewById(R.id.gameTitle)
        gameTitle.isSelected = true //Need this to make marquee work

        gameAbout = findViewById(R.id.gameAbout)
        //Just for example
        gameAbout.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur suscipit justo semper nunc tempus pretium. Sed eu nibh in est accumsan sollicitudin in sit amet ligula. Integer nec mollis nulla, ac tempus nisi. Pellentesque aliquet tempor mi at condimentum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Suspendisse eleifend ex nibh, nec finibus turpis tempor sed. Phasellus scelerisque mauris at ante ullamcorper convallis. Curabitur congue dictum nibh. Nam varius urna vitae ex luctus tempus at vel augue. Quisque eu mauris at quam finibus porttitor. Nulla id velit felis. Donec elementum at lorem id tristique. In hac habitasse platea dictumst. Phasellus at libero rutrum, lacinia libero eget, eleifend tortor. Vestibulum ornare augue eu suscipit egestas. Sed ac feugiat velit. Nunc volutpat lorem vitae neque vehicula, eget scelerisque ipsum accumsan. Etiam at lorem sit amet dui rhoncus ultrices. Vestibulum hendrerit nisi ac libero aliquam euismod. Nullam scelerisque nulla est, vitae sodales tellus lacinia ut. Donec bibendum, purus id porttitor mattis, ipsum arcu efficitur elit, ut luctus tortor leo in mauris. Aenean sed mi orci. Morbi porttitor tempus diam, vel porttitor dui dictum a."
        gameAbout.movementMethod = ScrollingMovementMethod() //Need this to make the about scrollable

    }
}