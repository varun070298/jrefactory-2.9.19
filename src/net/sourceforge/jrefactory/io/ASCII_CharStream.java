package net.sourceforge.jrefactory.io;

/**
 *  An implementation of interface CharStream, where the stream is assumed to
 *  contain only ASCII characters (without unicode processing).
 *
 *@author    Mike Atkinson
 *@author     <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 *@version    $Id: ASCII_CharStream.java,v 1.1 2003/10/23 14:19:20 mikeatkinson Exp $ 
 */
final class ASCII_CharStream extends CharStream {
	private char MASK;

	/**
	 *  Constructor for the ASCII_CharStream object
	 *
	 *@param  dstream      Description of Parameter
	 *@param  startline    Description of Parameter
	 *@param  startcolumn  Description of Parameter
	 *@param  buffersize   Description of Parameter
	 */
	public ASCII_CharStream(java.io.Reader dstream, int startline,
			int startcolumn, int buffersize, boolean fullChar) {
		if (inputStream != null) {
			throw new Error("\n   ERROR: Second call to the constructor of a ASCII_CharStream.  You must\n" +
					"       either use ReInit() or set the JavaCC option STATIC to false\n" +
					"       during the generation of this class.");
		}
		inputStream = dstream;
		line = startline;
		column = startcolumn - 1;

		available = bufsize = buffersize;
		buffer = new char[buffersize];
		bufline = new int[buffersize];
		bufcolumn = new int[buffersize];

		if (fullChar)
			MASK = 0xFFFF;
		else
			MASK = 0x00FF;
	}



	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public final String GetImage() {
		if (bufpos >= tokenBegin) {
			return new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
		}
		else {
			return new String(buffer, tokenBegin, bufsize - tokenBegin) +
					new String(buffer, 0, bufpos + 1);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  len  Description of Parameter
	 *@return      Description of the Returned Value
	 */
	public final char[] GetSuffix(int len) {
		char[] ret = new char[len];

		if ((bufpos + 1) >= len) {
			System.arraycopy(buffer, bufpos - len + 1, ret, 0, len);
		}
		else {
			System.arraycopy(buffer, bufsize - (len - bufpos - 1), ret, 0,
					len - bufpos - 1);
			System.arraycopy(buffer, 0, ret, len - bufpos - 1, bufpos + 1);
		}

		return ret;
	}



	/**
	 *  Description of the Method
	 *
	 *@return                          Description of the Returned Value
	 *@exception  java.io.IOException  Description of Exception
	 */
	public final char readChar() throws java.io.IOException {
		if (inBuf > 0) {
			--inBuf;
			return (char) (MASK & buffer[(bufpos == bufsize - 1) ? (bufpos = 0) : ++bufpos]);
		}

		if (++bufpos >= maxNextCharInd) {
			FillBuff();
		}

		char c = (char) (MASK & buffer[bufpos]);

		UpdateLineColumn(c);
		return (c);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  amount  Description of Parameter
	 */
	public final void backup(int amount) {

		inBuf += amount;
		if ((bufpos -= amount) < 0) {
			bufpos += bufsize;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  dstream      Description of Parameter
	 *@param  startline    Description of Parameter
	 *@param  startcolumn  Description of Parameter
	 *@param  buffersize   Description of Parameter
	 */
	public void ReInit(java.io.Reader dstream, int startline,
			int startcolumn, int buffersize) {
		inputStream = dstream;
		line = startline;
		column = startcolumn - 1;

		if (buffer == null || buffersize != buffer.length) {
			available = bufsize = buffersize;
			buffer = new char[buffersize];
			bufline = new int[buffersize];
			bufcolumn = new int[buffersize];
		}
		prevCharIsLF = prevCharIsCR = false;
		tokenBegin = inBuf = maxNextCharInd = 0;
		bufpos = -1;
	}



	/**
	 *  Description of the Method
	 */
	public void Done() {
		buffer = null;
		bufline = null;
		bufcolumn = null;
	}


	/**
	 *  Method to adjust line and column numbers for the start of a token. <BR>
	 *
	 *
	 *@param  newLine  Description of Parameter
	 *@param  newCol   Description of Parameter
	 */
	public void adjustBeginLineColumn(int newLine, int newCol) {
		int start = tokenBegin;
		int len;

		if (bufpos >= tokenBegin) {
			len = bufpos - tokenBegin + inBuf + 1;
		}
		else {
			len = bufsize - tokenBegin + bufpos + 1 + inBuf;
		}

		int i = 0;

		int j = 0;

		int k = 0;
		int nextColDiff = 0;
		int columnDiff = 0;

		while (i < len &&
				bufline[j = start % bufsize] == bufline[k = ++start % bufsize]) {
			bufline[j] = newLine;
			nextColDiff = columnDiff + bufcolumn[k] - bufcolumn[j];
			bufcolumn[j] = newCol + columnDiff;
			columnDiff = nextColDiff;
			i++;
		}

		if (i < len) {
			bufline[j] = newLine++;
			bufcolumn[j] = newCol + columnDiff;

			while (i++ < len) {
				if (bufline[j = start % bufsize] != bufline[++start % bufsize]) {
					bufline[j] = newLine++;
				}
				else {
					bufline[j] = newLine;
				}
			}
		}

		line = bufline[j];
		column = bufcolumn[j];
	}


	/**
	 *  Description of the Method
	 *
	 *@param  wrapAround  Description of Parameter
	 */
	private final void ExpandBuff(boolean wrapAround) {
		char[] newbuffer = new char[bufsize + 2048];
		int newbufline[] = new int[bufsize + 2048];
		int newbufcolumn[] = new int[bufsize + 2048];

		try {
			if (wrapAround) {
				System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
				System.arraycopy(buffer, 0, newbuffer,
						bufsize - tokenBegin, bufpos);
				buffer = newbuffer;

				System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
				System.arraycopy(bufline, 0, newbufline, bufsize - tokenBegin, bufpos);
				bufline = newbufline;

				System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
				System.arraycopy(bufcolumn, 0, newbufcolumn, bufsize - tokenBegin, bufpos);
				bufcolumn = newbufcolumn;

				maxNextCharInd = (bufpos += (bufsize - tokenBegin));
			}
			else {
				System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
				buffer = newbuffer;

				System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
				bufline = newbufline;

				System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
				bufcolumn = newbufcolumn;

				maxNextCharInd = (bufpos -= tokenBegin);
			}
		}
		catch (Throwable t) {
			throw new Error(t.getMessage());
		}

		bufsize += 2048;
		available = bufsize;
		tokenBegin = 0;
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.io.IOException  Description of Exception
	 */
	private final void FillBuff() throws java.io.IOException {
		if (maxNextCharInd == available) {
			if (available == bufsize) {
				if (tokenBegin > 2048) {
					bufpos = maxNextCharInd = 0;
					available = tokenBegin;
				}
				else if (tokenBegin < 0) {
					bufpos = maxNextCharInd = 0;
				}
				else {
					ExpandBuff(false);
				}
			}
			else if (available > tokenBegin) {
				available = bufsize;
			}
			else if ((tokenBegin - available) < 2048) {
				ExpandBuff(true);
			}
			else {
				available = tokenBegin;
			}
		}

		int i;
		try {
			if ((i = inputStream.read(buffer, maxNextCharInd,
					available - maxNextCharInd)) == -1) {
				inputStream.close();
				throw new java.io.IOException();
			}
			else {
				maxNextCharInd += i;
			}
			return;
		}
		catch (java.io.IOException e) {
			--bufpos;
			backup(0);
			if (tokenBegin == -1) {
				tokenBegin = bufpos;
			}
			throw e;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  c  Description of Parameter
	 */
	private final void UpdateLineColumn(char c) {
		column++;

		if (prevCharIsLF) {
			prevCharIsLF = false;
			line += (column = 1);
		}
		else if (prevCharIsCR) {
			prevCharIsCR = false;
			if (c == '\n') {
				prevCharIsLF = true;
			}
			else {
				line += (column = 1);
			}
		}

		switch (c) {
			case '\r':
				prevCharIsCR = true;
				break;
			case '\n':
				prevCharIsLF = true;
				break;
			case '\t':
				column--;
				column += (8 - (column & 07));
				break;
			default:
				break;
		}

		bufline[bufpos] = line;
		bufcolumn[bufpos] = column;
	}

}
