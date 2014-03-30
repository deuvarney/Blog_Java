package com.appspot.deuvarneyjava.blog;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.appspot.deuvarneyjava.services.*;

public class Rot13_servlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = req.getRequestDispatcher("/rot13jsp");
		req.setAttribute("rot_text", "");
		dispatcher.forward(req, resp);
		//super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String text = req.getParameter("text");
		String cypherText = "";
		
		String lowerLetters = "abcdefghijklmnopqrstuvwxyz";
		String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for(int i = 0; i <text.length(); i++){
			String letter = text.substring(i, i+1);
			if(lowerLetters.contains(letter)){
				int letterPos = lowerLetters.indexOf(letter);
				int newLetterPos =(letterPos + 13) % 26;
				cypherText = cypherText + lowerLetters.substring(newLetterPos, newLetterPos +1);
				
			}
			else if (upperLetters.contains(letter)){
				int letterPos = upperLetters.indexOf(letter);
				int newLetterPos =(letterPos + 13) % 26;
				cypherText = cypherText + upperLetters.substring(newLetterPos, newLetterPos +1);
			}
			else{
				cypherText = cypherText + letter;
			}
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/rot13jsp");
		req.setAttribute("rot_text", CommonService.escapeHTML(cypherText));
		dispatcher.forward(req, resp);
		//super.doPost(req, resp);
	}
}
