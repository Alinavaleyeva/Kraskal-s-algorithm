import src.gui.prototip;

public class Main {
    public static void main(String[] args) {
        // Создаем экземпляр окна
        prototip dialog = new prototip();
        // Настраиваем размер окна (опционально, если размер не установлен в самом GUI)
//        dialog.pack();
        dialog.setSize(600, 600);
        // Делаем окно видимым
        dialog.setVisible(true);
        // Завершаем программу при закрытии окна (если это необходимо)
        System.exit(0);
    }
}