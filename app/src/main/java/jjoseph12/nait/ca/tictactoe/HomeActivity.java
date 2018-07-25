package jjoseph12.nait.ca.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewTitle;
    private Button button, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button = (Button)findViewById(R.id.button_multiplayer);
        button2 = (Button)findViewById(R.id.button_singleplayer);
        button3 = (Button)findViewById(R.id.button_aboutgame);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button_aboutgame:
            {
                Intent intent = new Intent(this, WebViewActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.button_multiplayer:
            {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.button_singleplayer:
            {
                Intent intent = new Intent(this, Main2Activity.class);
                this.startActivity(intent);
                break;
            }
        }
    }
}
