//  Copyright (c) 2012 The Board of Trustees of The University of Alabama
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions
//  are met:
//
//  1. Redistributions of source code must retain the above copyright
//  notice, this list of conditions and the following disclaimer.
//  2. Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the distribution.
//  3. Neither the name of the University nor the names of the contributors
//  may be used to endorse or promote products derived from this software
//  without specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
//  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
//  THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
//  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
//   SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
//  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
//  STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
//  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.

package edu.ua.caps.ObjectMapper;

import android.R.integer;

//SCANNER CLASS
public class OMScanner {
		private char[] _baseString;
		private int _index;
		
		public OMScanner(String string){
			_baseString = string.toCharArray();
			_index = 0;
		}
		
		public String getScanString() {
			return new String(_baseString);
		}
		
		public int getScanIndex(){
			return _index;
		}
		
		public void setScanIndex(int index){
			_index = index;
		}
		
		public char readCharacter() {
			char character = _baseString[_index];
			_index++;
			return character;
		}
		
		public String readNextTagValue() {
			String string = "";
			
			boolean done = false;
			int tempIndex = _index;
			boolean saveChar = false;
			
			while (!done && tempIndex < _baseString.length) {
				char readCharacter = this.readCharacter();
				
				//When you find this, the value is coming next, start saving characters
				if (readCharacter == '>') {
					saveChar = true;
				}
				else if (saveChar) { //start saving characters
					if (readCharacter == '<') {
						//We've reached the end, so skip to the end of the string
						this.skipToString(">");
						done = true;
					} 
					else {
						string = string + readCharacter;
					}
				}
			}
			
			return string;
		}
		
		public char nextCharacter(){
			return _baseString[_index];
		}
		
		public String scanToChar(char c) {
			String string = "";
			
			boolean done = false;
			while (!done) {
				char newChar = this.readCharacter();
				string = string + newChar;
				
				if (newChar == c) {
					done = true;
				}
			}
			
			return string;
		}
		
		public String scanToString(String string) {
			String returnString = "";
			
			String potentialMatchString = "";
			
			boolean done = false;
			while (!done) {
				char newChar = ' ';
				if (this._baseString.length > this._index) {
					newChar = this.readCharacter();
				} 
				else {
					return returnString + potentialMatchString;
				}
				
				if (potentialMatchString.equals("")) {
					if (newChar == string.charAt(0)) {
						if (string.length() == 1) {
							done = true;
							break;
						} else {
							potentialMatchString = potentialMatchString + newChar;
							continue;
						}
						
					}
				}
				else {
					//We are done!
					if ((potentialMatchString+newChar).equals(string)) {
						done = true;
						break;
					}
					//It is a continued match. Let's keep going and see how it plays out
					else if ((potentialMatchString+newChar).equals(string.substring(0, (potentialMatchString+newChar).length()))) {
						potentialMatchString = potentialMatchString + newChar;
						continue;
					}
					else {
						//Add and reset!
						returnString = returnString + potentialMatchString + newChar;
						potentialMatchString = "";
						continue;
					}
				}
				
				returnString = returnString + newChar;
			}
			
			return returnString;
		}
		
		public void skipToString(String skipString) {
			String string = "";
			int tempIndex = _index;
			
			boolean done = false;
			while (!done && tempIndex < _baseString.length) {
				
				string = string + _baseString[tempIndex];
				tempIndex++;
				
				if (string.contains(skipString)) {
					_index = tempIndex;
					done = true;
				}
			}
		}
		
		public String nextXMLTag() {
			boolean done = false;
			int tempIndex = _index;
			String string = "";
			boolean saveChar = false;
			
			while (!done && tempIndex < _baseString.length) {
				char c = _baseString[tempIndex];
				if (c == '<') {
					saveChar = true;
				} else if (saveChar) {
					if (_baseString[tempIndex] == '>') {
						done = true;
					} else if (_baseString[tempIndex] == ' ') {
						done = true;
						
						//Get all things after tag
						String attributesString = " ";
						while (_baseString[tempIndex] != '>') {
							attributesString = attributesString + _baseString[tempIndex];
							tempIndex++;
						}
						
						//If those things contain a end tag, return it!
						if (attributesString.contains(" /")) {
							return string + " /";
						}
						
					} else {
						string = string + _baseString[tempIndex];
					}
				}

				tempIndex++;
			}
			
			
			return string;
		}
		
		public boolean hasNext() {
			if (_index < _baseString.length) {
				return true;
			}
			
			return false;
		}
		
		public boolean endOfTag(String tag) {
			boolean done = false;
			int tempIndex = _index;
			String string = "";
			boolean saveChar = false;
			
			while (!done && tempIndex < _baseString.length) {
				char c = _baseString[tempIndex];
				if (c == '<') {
					saveChar = true;
				} else if (saveChar) {
					if (_baseString[tempIndex] == '>') {
						done = true;
					} else {
						string = string + _baseString[tempIndex];
					}
				}

				tempIndex++;
			}
			
			//Check to see if the tag is at its end
			if (string.contains("/"+tag)) {
				return true;
			}
			else {
				return false;
			}
			
		}
		
		public void skipEmptyTag() {
			this.skipToString(" />");
		}
	}
