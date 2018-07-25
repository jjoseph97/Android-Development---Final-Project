package jjoseph12.nait.ca.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    //Two dimensional array
    private Button[][] buttons = new Button[3][3];

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    SharedPreferences settings;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settings = PreferenceManager.getDefaultSharedPreferences(Main2Activity.this);
        settings.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.layout_main);
        String bgColor = settings.getString("main_bg_color_list","#ffffff");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        changeTextColors(bgColor);

        textViewPlayer1 = (TextView)findViewById(R.id.text_view_p1);
        textViewPlayer2 = (TextView)findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = (Button)findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = (Button)findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if(!((Button)v).getText().toString().equals(""))
        {
            return;
        }

        ((Button) v).setText("X");
        roundCount++;
        if (checkForWin())
        {
            player1Wins();
        } else if (roundCount == 9)
        {
            draw();
        }
        //random number generator
        Random rng = new Random();
        boolean valid = false;
        do {
            int i = rng.nextInt(3);
            int j = rng.nextInt(3);
            if (buttons[i][j].getText().toString().equals(""))
            {
                buttons[i][j].setText("O");
                valid = true;
            }
            else
            {
                valid = false;
            }
        } while (!valid);
        roundCount++;
        if (checkForWin()) {
            player2Wins();
        } else if (roundCount == 9) {
            draw();
        }
    }

    //returns true to check for a winner
    private boolean checkForWin()
    {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //checks all rows horizontally
        for (int i = 0; i < 3; i++)
        {
            //compares the three fields close to each other
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    //makes sure its not three times an empty field
                    && !field[i][0].equals(""))
            {
                return true;
            }
        }

        //check all columns vertically
        for (int i = 0; i < 3; i++)
        {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(""))
            {
                return true;
            }
        }

        //Diagonal Check
        //check for diagonals starting top left to bottom right
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(""))
        {
            return true;
        }

        //check for diagonals starting top right to bottom left
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals(""))
        {
            return true;
        }

        return false;
    }

    private void player1Wins()
    {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins()
    {
        player2Points++;
        Toast.makeText(this, "Computer Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw()
    {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    //Show the points for Player 1 and Player 2
    private void updatePointsText()
    {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    //Reset all buttons to an empty string
    //Set the roundcount = 0 and Player 1 turn = true
    private void resetBoard()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        //player1Turn = true;
    }

    //reset the entire board
    private void resetGame()
    {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        //outState.putBoolean("player1turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        //player1Turn = savedInstanceState.getBoolean("player1turn");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = settings.getString("main_bg_color_list","#ffffff");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
        changeTextColors(bgColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_item_preferences:
            {
                Intent intent = new Intent(this, PrefsActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_exit:
            {
                Intent intent = new Intent(this, HomeActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    private void changeTextColors(String color)
    {
        TextView text_view_p1 = (TextView) findViewById(R.id.text_view_p1);
        TextView text_view_p2 = (TextView) findViewById(R.id.text_view_p2);
        Button button_reset = (Button) findViewById(R.id.button_reset);

        if (color.equals("#000000") || color.equals("#000099"))
        {
            text_view_p1.setTextColor(Color.WHITE);
            text_view_p2.setTextColor(Color.WHITE);
        }
        else
        {
            text_view_p1.setTextColor(Color.BLACK);
            text_view_p2.setTextColor(Color.BLACK);
        }
    }
}
