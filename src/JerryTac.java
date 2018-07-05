/**
 @author Tyler Meadows
 @author Mike McFall
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class JerryTac extends JFrame {
    //Variable Declaration
    private JerryTacToe jry;
    private JLayeredPane boardPane;
    private JPanel gameSpace;
    private ButtonGroup btnGroup;
    private JLabel user, grid, winner;
    private JLabel [] btn;
    private JLabel [] btnLbls;
    private JButton play;
    private JRadioButton you, comp;
    private ImageIcon grey,blue,grn,back;
    private Integer compfirst=1;

    public static void main(String args[])
    {
        new JerryTac().setVisible(true);
    }

    public JerryTac()
    {
        initComponents();
    }

    private void initComponents()
    {
        jry = new JerryTacToe();
        btn = new JLabel[10];
        btnLbls = new JLabel[10];
        grid = new JLabel();
        user = new JLabel();
        winner = new JLabel();
        gameSpace = new JPanel();
        boardPane = new JLayeredPane();
        play= new JButton();
        you = new JRadioButton();
        comp = new JRadioButton();
        btnGroup = new ButtonGroup();
        grey = new ImageIcon(getClass().getResource("offBtn.gif"));
        blue = new ImageIcon(getClass().getResource("blueBtn.gif"));
        grn = new ImageIcon(getClass().getResource("greenBtn.gif"));
        back = new ImageIcon(getClass().getResource("backg.gif"));
        btnGroup.add(you);
        btnGroup.add(comp);

        UIManager.put("OptionPane.background",new Color(62,62,62));
        UIManager.put("Panel.background",new Color(62,62,62));
        UIManager.put("OptionPane.messageForeground", Color.white);

        for(int i=1; i<10; i++)
        {
            btn[i]=new JLabel();
            btn[i].setIcon(grey);
            btn[i].setEnabled(false);
            isValid[i]=false;
            btn[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e){btnMouseClicked(e);}});
            btnLbls[i] = new JLabel();
            btnLbls[i].setFont(new Font("Tahoma", 0, 18));
            btnLbls[i].setText(Integer.toString(i));
            isValid[i]=false;
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jerry Tac Toe");
        setResizable(false);

        boardPane.setBackground(new Color(62, 62, 62));
        boardPane.setOpaque(true);

        gameSpace.setBackground(new Color(74, 74, 74));
        gameSpace.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        gameSpace.setLayout(null);
        gameSpace.setBounds(10, 10, 380, 340);
        boardPane.add(gameSpace);

        btnLbls[1].setBounds(41, 33, 20, 30);
        btnLbls[2].setBounds(181, 33, 20, 30);
        btnLbls[3].setBounds(321, 33, 20, 30);
        btnLbls[4].setBounds(110, 153, 20, 30);
        btnLbls[5].setBounds(181, 153, 20, 30);
        btnLbls[6].setBounds(251, 153, 20, 30);
        btnLbls[7].setBounds(41, 274, 20, 30);
        btnLbls[8].setBounds(181, 274, 20, 30);
        btnLbls[9].setBounds(321, 274, 20, 30);

        btn[1].setBounds(20,20,60,60);
        btn[2].setBounds(160,20,60,60);
        btn[3].setBounds(300,20,60,60);
        btn[4].setBounds(90,140,60,60);
        btn[5].setBounds(160,140,60,60);
        btn[6].setBounds(230,140,60,60);
        btn[7].setBounds(20,260,60,60);
        btn[8].setBounds(160,260,60,60);
        btn[9].setBounds(300,260,60,60);

        for(int i=1; i<10; i++)
        {
            gameSpace.add(btnLbls[i]);
            gameSpace.add(btn[i]);
        }

        grid.setBounds(45,0,380,340);
        grid.setIcon(back);
        grid.setFocusable(false);
        gameSpace.add(grid);

        you.setBackground(new Color(62, 62, 62));
        you.setForeground(new Color(255, 255, 255));
        you.setText("Player");
        you.setSelected(true);
        you.setBounds(400, 30, 110, 23);
        boardPane.add(you);

        comp.setBackground(new Color(62, 62, 62));
        comp.setForeground(new Color(255, 255, 255));
        comp.setText("Computer");
        comp.setBounds(400, 50, 100, 23);
        boardPane.add(comp);

        user.setForeground(new Color(255, 255, 255));
        user.setHorizontalAlignment(SwingConstants.CENTER);
        user.setText("First Player");
        user.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        user.setBounds(405, 10, 85, 20);
        boardPane.add(user);

        play.setText("Play");
        play.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {playClicked(e);}});
        play.setBounds(400, 100, 100, 23);
        boardPane.add(play, JLayeredPane.DEFAULT_LAYER);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardPane, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(boardPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );

        pack();
    }

    private void playClicked(MouseEvent e)
    {
        if(you.isSelected())
            reset();
        else
        {
            reset();
            doCompMove(false);
        }
        play.setVisible(false);
    }

    private void btnMouseClicked(MouseEvent e)
    {
        for(int j=1; j<10; j++)
        {
            if(e.getSource()==btn[j])
            {
                if(isValid[j])
                {
                    btn[j].setIcon(blue);
                    btnLbls[j].setText("X");
                    jry.playMove(JerryTacToe.HUMAN, j);
                    isValid[j]=false;
                }
                else
                    return;
            }
        }

        if(jry.boardIsFull() &&  !jry.isAWin(JerryTacToe.HUMAN) &&  !jry.isAWin(JerryTacToe.COMPUTER))
        {
            play.setText("Play again?");
            play.setVisible(true);
            JOptionPane.showMessageDialog(boardPane, "Tie Game!","Results", JOptionPane.INFORMATION_MESSAGE, grey);
            for(int j=1; j<10; j++)
                isValid[j]=false;
            return;
        }
        if(jry.isAWin(JerryTacToe.HUMAN))
        {
            play.setText("Play again?");
            play.setVisible(true);
            JOptionPane.showMessageDialog(boardPane, "Human Wins!","Results", JOptionPane.INFORMATION_MESSAGE, blue);
            for(int j=1; j<10; j++)
                isValid[j]=false;
            return;
        }
        if(compfirst==1)
            doCompMove(false);
        else
            doCompMove(true);
        if(jry.isAWin(JerryTacToe.COMPUTER) )
        {
            play.setText("Play again?");
            play.setVisible(true);
            JOptionPane.showMessageDialog(boardPane, "Machine Wins!","Results", JOptionPane.INFORMATION_MESSAGE, grn);
            for(int j=1; j<10; j++)
                isValid[j]=false;
            return;
        }
    }

    public void doCompMove(boolean thinkAboutIt)
    {
        Best compMove;

        if( thinkAboutIt )
            compMove = jry.chooseMove( JerryTacToe.COMPUTER );
        else
        {
            compMove = new Best(0, (int)(9*Math.random()+1));
        }

        if(jry.makeBlock()!=0)
            compMove = new Best(0, jry.makeBlock());
        if(jry.goForWin()!=0)
            compMove = new Best(0, jry.goForWin());

        compfirst=2;
        btn[compMove.circle].setIcon(grn);
        btnLbls[compMove.circle].setText("O");
        jry.playMove(JerryTacToe.COMPUTER, compMove.circle);
        isValid[compMove.circle]=false;
    }

    public void reset()
    {
        jry.clearBoard();
        for(int i=1; i<10;i++)
        {
            btn[i].setIcon(grey);
            btn[i].setEnabled(true);
            btnLbls[i].setText(Integer.toString(i));
            isValid[i]=true;
            winner.setText("");
            compfirst=1;
        }
    }
    private boolean [] isValid = new boolean[10];
}
