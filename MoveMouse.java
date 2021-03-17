// Move Mouse
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UnsupportedLookAndFeelException;

class MoveMouse {
    private volatile boolean isRunning = true;
    private Wrappers.IntegerWrapper time;       // how long to wait before checking mouse location
    private Wrappers.IntegerWrapper distance;   // distance to move mouse
    private SettingsFrame settingsFrame;
    private static final String PAUSE = "Pause Service";
    private static final String START = "Start Service";
    public static void main(String[] args) {
        try {
            MoveMouse mm = new MoveMouse(UserSettings.load());
            mm.mainLoop();
        } catch(AWTException
            |InterruptedException
            |UnsupportedLookAndFeelException
            |IllegalAccessException
            |InstantiationException
            |ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MoveMouse() {
        this(UserSettings.load());
    }

    public MoveMouse(String[] args) {
        try {
            time = new Wrappers.IntegerWrapper(Integer.valueOf(args[0]));
            distance = new Wrappers.IntegerWrapper(Integer.valueOf(args[1]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            time = new Wrappers.IntegerWrapper(UserSettings.DEFAULT_TIME);
            distance = new Wrappers.IntegerWrapper(UserSettings.DEFAULT_DISTANCE);
        }
    }

    private void mainLoop() throws 
            AWTException, 
            InterruptedException,
            UnsupportedLookAndFeelException,
            IllegalAccessException,
            InstantiationException,
            ClassNotFoundException {
        // If System Tray is supported then add Mouse Move icon
        if (SystemTray.isSupported()) {
            initSystemTray();
            settingsFrame = new SettingsFrame("Move Mouse Settings", time, distance);
        }
        
        Point prevpoint = null;
        Point currpoint = null;
        Robot robot = new Robot();  // robot used to move mouse
        boolean moveRight = false;  // move mouse left or right
        while (true) {
            if (isRunning) {
                prevpoint = MouseInfo.getPointerInfo().getLocation();    // save the last mouse location
                Thread.sleep(time.getValue());                           // wait some time
                currpoint = MouseInfo.getPointerInfo().getLocation();    // get current mouse pointer location
                
                // if the mouse location has not changed then move mouse
                if (currpoint.getX() == prevpoint.getX() && currpoint.getY() == prevpoint.getY()) {
                    // move mouse
                    robot.mouseMove((int)currpoint.getX() + (moveRight ? distance.getValue() : -distance.getValue()), (int)currpoint.getY());
                    moveRight = !moveRight;
                }
            }
        }
    }

    private void initSystemTray() throws AWTException {
        // This will contain the quit button
        PopupMenu popupMenu = new PopupMenu();
        // store icon
        Image image = Toolkit.getDefaultToolkit().getImage("icons/smallicon.png");
        // create tray icon
        TrayIcon trayIcon = new TrayIcon(image, "Move Mouse", popupMenu);

        // create settings menu item
        MenuItem settingsMenuItem = new MenuItem("Settings");
        // open a pop up window when clicked
        settingsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settingsFrame.showSettingsWindow();
            }
        });

        // create quit menu item
        MenuItem quitMenuItem = new MenuItem("Quit Move Mouse");
        // when menu item is click, then quit
        quitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserSettings.save(time.getValue(), distance.getValue());
                System.exit(1);
            }
        });

        // create pause button menu item
        MenuItem pauseMenuItem = new MenuItem(PAUSE);
        pauseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isRunning = !isRunning;
                pauseMenuItem.setLabel(isRunning ? PAUSE : START);
            }
        });

        popupMenu.add(pauseMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(settingsMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(quitMenuItem);
        trayIcon.setPopupMenu(popupMenu);
        SystemTray.getSystemTray().add(trayIcon);
    }
}