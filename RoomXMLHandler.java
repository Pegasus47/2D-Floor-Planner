import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class RoomXMLHandler {

    // Method to save rooms to XML
    public static void saveRoomsToXML(ArrayList<Rooms> rooms, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Root element
            Element rootElement = doc.createElement("Rooms");
            doc.appendChild(rootElement);

            for (Rooms room : rooms) {
                Element roomElement = doc.createElement("Room");

                // Add room attributes
                roomElement.setAttribute("x", String.valueOf(room.rectangle.x));
                roomElement.setAttribute("y", String.valueOf(room.rectangle.y));
                roomElement.setAttribute("width", String.valueOf(room.rectangle.width));
                roomElement.setAttribute("height", String.valueOf(room.rectangle.height));

                // Add color as an attribute (store RGB values as a single string)
                roomElement.setAttribute("color", String.format("%d,%d,%d",
                        room.color.getRed(), room.color.getGreen(), room.color.getBlue()));

                rootElement.appendChild(roomElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(filePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Rooms saved to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Method to load rooms from XML
    public static ArrayList<Rooms> loadRoomsFromXML(String filePath) {
        ArrayList<Rooms> rooms = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Room");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Extract room attributes
                    int x = Integer.parseInt(element.getAttribute("x"));
                    int y = Integer.parseInt(element.getAttribute("y"));
                    int width = Integer.parseInt(element.getAttribute("width"));
                    int height = Integer.parseInt(element.getAttribute("height"));

                    // Extract color information
                    String[] rgb = element.getAttribute("color").split(",");
                    Color color = new Color(
                            Integer.parseInt(rgb[0]),
                            Integer.parseInt(rgb[1]),
                            Integer.parseInt(rgb[2])
                    );

                    // Create a new room
                    Rooms room = new Rooms(new Rectangle(x, y, width, height), color);
                    rooms.add(room);
                }
            }
            System.out.println("Rooms loaded from " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

}
