
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PanelG extends JPanel {
    private java.util.List<Node> nodes;
    private List<Edge> edges;
    private Node selectedNode;
    private Color vertexColor = Color.BLACK;
    private Color edgeColor = Color.BLACK;
    private int edgeThickness = 3;

    public PanelG() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        selectedNode = null;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Node node = findNode(e.getX(), e.getY());
                    if (node == null) {
                        nodes.add(new Node(e.getX(), e.getY()));
                    } else {
                        selectedNode = node;
                    }
                    repaint();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    Node node = findNode(e.getX(), e.getY());
                    if (node != null) {
                        removeNode(node);
                    } else {
                        Edge edge = findEdge(e.getX(), e.getY());
                        if (edge != null) {
                            removeEdge(edge);
                        }
                    }
                    repaint();
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    Node node = findNode(e.getX(), e.getY());
                    if (node != null) {
                        selectedNode = node;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (selectedNode != null) {
                        Node node = findNode(e.getX(), e.getY());
                        if (node != null && node != selectedNode) {
                            int edgeWeight = 1; // Пример с фиксированным весом, можно использовать JOptionPane.showInputDialog для ввода пользователем
                            edges.add(new Edge(selectedNode, node, edgeWeight));
                        }
                        selectedNode = null;
                    }
                    repaint();
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    selectedNode = null;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e) && selectedNode != null) {
                    selectedNode.setPoint(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    private Node findNode(int x, int y) {
        for (Node node : nodes) {
            if (node.contains(x, y)) {
                return node;
            }
        }
        return null;
    }

    private Edge findEdge(int x, int y) {
        for (Edge edge : edges) {
            Point p1 = edge.getStart().getPoint();
            Point p2 = edge.getEnd().getPoint();
            if (isPointNearLine(x, y, p1.x, p1.y, p2.x, p2.y)) {
                return edge;
            }
        }
        return null;
    }

    private boolean isPointNearLine(int x, int y, int x1, int y1, int x2, int y2) {
        double distance = Line2D.ptSegDist(x1, y1, x2, y2, x, y);
        return distance < 5.0; // You can adjust this threshold
    }

    private void removeNode(Node node) {
        nodes.remove(node);
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            if (edge.getStart() == node || edge.getEnd() == node) {
                iterator.remove();
            }
        }
    }

    private void removeEdge(Edge edge) {
        edges.remove(edge);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(edgeThickness));

        Font boldFont = new Font("Default", Font.BOLD, 14); // Установка жирного шрифта размером 14
        g2d.setFont(boldFont);

        // Рисуем рёбра
        for (Edge edge : edges) {
            g2d.setColor(edgeColor);
            Point start = edge.getStart().getPoint();
            Point end = edge.getEnd().getPoint();
            g2d.drawLine(start.x, start.y, end.x, end.y);

            // Отображение веса ребра
            String weightText = "[" + edge.getWeight() + "]"; // Вес ребра в квадратных скобках
            int midX = (start.x + end.x) / 2;
            int midY = (start.y + end.y) / 2;

            // Смещение текста для избежания пересечения с линией
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(weightText);
            int textHeight = fm.getHeight();
            g2d.drawString(weightText, midX - textWidth / 2, midY - textHeight / 2);
        }

        // Рисуем узлы (вершины) без весов
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            g2d.setColor(vertexColor);
            int radius = node.getRadius();
            int diameter = radius * 4; // Размер увеличен в два раза, чтобы кружки были в два раза больше
            int x = node.getPoint().x - radius * 2; // Адаптация координат под увеличенный размер
            int y = node.getPoint().y - radius * 2;

            // Рисуем большой круг
            g2d.drawOval(x, y, diameter, diameter);

            // Рисуем номер кружка жирным шрифтом
            String number = String.valueOf(i + 1);
            FontMetrics fm = g2d.getFontMetrics();

            g2d.setFont(boldFont);
            fm = g.getFontMetrics(boldFont); // Получаем FontMetrics уже для жирного шрифта
            double textWidth = fm.stringWidth(number);
            int textHeight = fm.getHeight();
            // Расчет координат для центровки текста
            int textX = (int) (node.getPoint().x - (textWidth / 2));
            int textY = (int) (node.getPoint().y + (textHeight / 4));
            g2d.drawString(number, textX, textY);
        }
    }
}