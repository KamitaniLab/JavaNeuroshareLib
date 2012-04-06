/**
 * @author 武宮 誠 - 「Makoto Takemiya」 <br /> 
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 * 
 * @version 2011/11/15
 */
package jp.atr.dni.api.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import jp.atr.dni.api.nsn.data.NSNChannelInfo;
import jp.atr.dni.api.nsn.enums.NSNChannelType;
import jp.atr.dni.api.nsn.enums.NSNChannelTypeCode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOM-parser 
 * 
 * @author 武宮　誠 「Makoto Takemiya」<br />
 * （株）国際電気通信基礎技術研究・脳情報研究所・神経情報学研究室<br/>
 * 「ATR - Computational Neuroscience Laboratories, Department of Neuroinformatics」
 *
 * @version 2011/11/15
 */
public class InfoFileXMLParser {

   public static final String CH_INFO = "chInfo";

   public static final String CH = "ch";

   public static final String CH_NAME = "chName";

   public static final String NEUROSHARE_TYPE = "neuroshareType";

   public static final String CH_TYPE = "chType";

   public static final String CH_TYPE_CODE = "chTypeCode";

   public static final String COMMENT = "comment";

   public static final String NUM_ATTR = "num";

   /** constructed object classes */
   private HashSet<String> objectTypes = new HashSet<String>();

   /** a mapping of class variables to default values */
   private HashMap<String, String> classVariables = new HashMap<String, String>();

   /** properties for each class */
   private HashMap<String, HashMap<String, String>> classProperties = new HashMap<String, HashMap<String, String>>();

   /** inheritance lists for each class */
   private HashMap<String, ArrayList<String>> classHierarchy = new HashMap<String, ArrayList<String>>();

   /** attributes for each class */
   private HashMap<String, ArrayList<String>> classAttributes = new HashMap<String, ArrayList<String>>();

   /**
    * Default constructor.
    */
   public InfoFileXMLParser() {
      super();
   }

   /**
    * 
    * @param xmlFile
    * @return
    * @throws Exception
    */
   public List<NSNChannelInfo> parseXMLFile(String xmlFile) throws Exception {

      List<NSNChannelInfo> nsnChannelInfos = new ArrayList<NSNChannelInfo>();

      // open the document
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      Document document = factory.newDocumentBuilder().parse(new File(xmlFile));
      Element root = document.getDocumentElement();

      // get the class properties
      NodeList nodes = root.getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
         if (!(nodes.item(i) instanceof Element)) {
            continue;
         }

         Element element = (Element) nodes.item(i);

         String name = element.getNodeName();
         for (int ndx = 0; ndx < element.getAttributes().getLength(); ndx++) {
            classVariables.put(name, element.getAttributes().item(ndx).getNodeName());
            // System.out.println(name + "---->" + element.getAttributes().item(ndx).getNodeName());
         }

         // String value = element.getAttribute("default");
         // classVariables.put(name, value);
      }

      // load the class hierarchy
      for (int i = 0; i < nodes.getLength(); i++) {
         if (!(nodes.item(i) instanceof Element)) {
            continue;
         }

         Element element = (Element) nodes.item(i);
         // System.out.println(element.getNodeName());

         traverseElement(element, new ArrayList<String>(), objectTypes, new ArrayList<String>());
      }

      return nsnChannelInfos;
   }

   public List<NSNChannelInfo> parseXML(String xml) throws Exception {

      List<NSNChannelInfo> nsnChannelInfos = new ArrayList<NSNChannelInfo>();

      // open the document
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
      Element root = document.getDocumentElement();

      // get the class properties
      NodeList nodes = root.getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
         if (!(nodes.item(i) instanceof Element)) {
            continue;
         }

         Element element = (Element) nodes.item(i);
         String className = element.getNodeName();

         if (className.equalsIgnoreCase(CH)) {
            NSNChannelInfo channelInfo = new NSNChannelInfo();

            int num = Integer.parseInt(element.getAttribute(NUM_ATTR));
            channelInfo.setChannelNumber(num);

            for (int n = 0; n < element.getChildNodes().getLength(); n++) {
               if (!(element.getChildNodes().item(n) instanceof Node)) {
                  continue;
               }

               Node node = (Node) element.getChildNodes().item(n);
               className = node.getNodeName();
               // System.out.println("child class: " + className);
               if (className.equalsIgnoreCase(CH_TYPE)) {
                  for (int c = 0; c < node.getChildNodes().getLength(); c++) {
                     if (!(node.getChildNodes().item(c) instanceof Node)) {
                        continue;
                     }
                     Node nodeChild = node.getChildNodes().item(c);
                     String chType = nodeChild.getNodeValue();
                     channelInfo.setChannelType(chType);
                     break;
                  }
               } else if (className.equalsIgnoreCase(CH_TYPE_CODE)) {
                  for (int c = 0; c < node.getChildNodes().getLength(); c++) {
                     if (!(node.getChildNodes().item(c) instanceof Node)) {
                        continue;
                     }
                     Node nodeChild = node.getChildNodes().item(c);
                     int chTypeCode = Integer.parseInt(nodeChild.getNodeValue());
                     channelInfo.setChannelTypeCode(NSNChannelTypeCode.fromInt(chTypeCode));
                     break;
                  }
               } else if (className.equalsIgnoreCase(CH_NAME)) {
                  for (int c = 0; c < node.getChildNodes().getLength(); c++) {
                     if (!(node.getChildNodes().item(c) instanceof Node)) {
                        continue;
                     }
                     Node nodeChild = node.getChildNodes().item(c);
                     String entityLabel = nodeChild.getNodeValue();
                     channelInfo.setEntityLabel(entityLabel);
                     break;
                  }
               } else if (className.equalsIgnoreCase(NEUROSHARE_TYPE)) {
                  for (int c = 0; c < node.getChildNodes().getLength(); c++) {
                     if (!(node.getChildNodes().item(c) instanceof Node)) {
                        continue;
                     }
                     Node nodeChild = node.getChildNodes().item(c);
                     int entityType = Integer.parseInt(nodeChild.getNodeValue());
                     channelInfo.setEntityType(NSNChannelType.fromInt(entityType));
                     break;
                  }
               } else if (className.equalsIgnoreCase(COMMENT)) {
                  for (int c = 0; c < node.getChildNodes().getLength(); c++) {
                     if (!(node.getChildNodes().item(c) instanceof Node)) {
                        continue;
                     }
                     Node nodeChild = node.getChildNodes().item(c);
                     String commentText = nodeChild.getNodeValue();
                     channelInfo.setComment(commentText);
                     break;
                  }
               }
            }

            // Before adding the created channel, validate the required fields
            boolean toAdd = true;
            if (channelInfo.getChannelNumber() == null || channelInfo.getEntityLabel() == null
                  || channelInfo.getEntityType() == null) {
               toAdd = false;
            }

            if (toAdd && channelInfo.getChannelType() == null) {
               if (channelInfo.getChannelTypeCode() == null) {
                  toAdd = false;
               } else {
                  channelInfo.setChannelType(channelInfo.getChannelTypeCode().toString());
               }
            }

            if (toAdd) {
               nsnChannelInfos.add(channelInfo);
            }
         }
      }

      // System.out.println(nsnChannelInfos);

      return nsnChannelInfos;
   }

   /**
    * 
    * @param element
    * @param hierarchy
    * @param types
    */
   private void traverseElement(Element element, ArrayList<String> hierarchy, HashSet<String> types,
         ArrayList<String> properties) {

      // get the inherited class attributes
      HashMap<String, String> attributes = new HashMap<String, String>();

      if (hierarchy.size() > 0) {
         String parentClass = hierarchy.get(hierarchy.size() - 1);
         for (String property : classProperties.get(parentClass).keySet()) {
            attributes.put(property, classProperties.get(parentClass).get(property));
         }
      } else {
         for (String property : classVariables.keySet()) {
            attributes.put(property, classVariables.get(property));
         }
      }

      // get the specified class attributes
      String className = element.getNodeName();
      for (String attribute : attributes.keySet()) {
         if (element.getAttribute(attribute).length() > 0) {
            attributes.put(attribute, element.getAttribute(attribute));
         }
      }
      // System.out.println(className + "::::: " + attributes);
      classProperties.put(className, attributes);

      // get additional class atributes
      NodeList nodes = element.getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
         if (!(nodes.item(i) instanceof Element)) {
            continue;
         }

         Element child = (Element) nodes.item(i);
         if (child.getNodeName().equalsIgnoreCase("attribute")) {
            if (!child.getAttribute("name").equalsIgnoreCase("ID")) {
               properties.add(child.getAttribute("name"));
            }
         }
      }
      classAttributes.put(className, properties);

      // add the class to the hierarchy
      types.add(className);
      hierarchy.add(className);
      classHierarchy.put(className, hierarchy);

      // traverse child classes
      for (int i = 0; i < nodes.getLength(); i++) {
         if (!(nodes.item(i) instanceof Element)) {
            continue;
         }

         Element child = (Element) nodes.item(i);
         if (child.getNodeName().equalsIgnoreCase("class")) {
            traverseElement(child, (ArrayList) hierarchy.clone(), types, (ArrayList) properties.clone());
         }
      }
   }
}
