package com.bzamani.framework.common.utility;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ParseTree {

	
	public static String createTreeXML(TreeNode nodes){
		return parseXML(nodes,new StringBuffer()).toString();
	}

	private static StringBuffer parseXML(TreeNode nodes,StringBuffer xml) {
		for (TreeNode node : nodes.getChilds()) {
			
			xml.append("<item text=\"" + node.getText() + "\" id=\"" + node.getId() + "\" im0=\"" + node.getIm0() + "\" im1=\"" + node.getIm1() + "\" im2=\"" + node.getIm2() + "\" ");
			
			if(!node.getAttrMap().isEmpty()){
				Set setAttr = node.getAttrMap().entrySet();
				Iterator itratorAttr = setAttr.iterator(); 
				while(itratorAttr.hasNext()){
					Map.Entry attr = (Map.Entry) itratorAttr.next();
					xml.append( " " + attr.getKey().toString() + "=\"" + attr.getValue().toString() + "\" ");
				}
			}
			
			if (node.getChilds() != null && node.getChilds().size() > 0) {
				xml.append(">");
				parseXML(node,xml);
				xml.append("</item>");
			} else if((node.getChilds() == null || node.getChilds().size() == 0) && node.getChildCount() > 0){
				xml.append(">");
				xml.append("<item text=\"...\" im0=\"leaf.gif\" id=\"t"+node.getId() +"\"/>");
				xml.append("</item>");
			}
			else{
				xml.append("/>");
			}
		}
		return xml;
	}
	
}
