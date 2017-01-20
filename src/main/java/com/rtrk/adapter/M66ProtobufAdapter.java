package com.rtrk.adapter;

import com.rtrk.atcommands.ATCommand.HTTPCommand;
import com.rtrk.atcommands.ATCommand.HTTPMessageType;
import com.rtrk.atcommands.ATCommand.MMSCommand;
import com.rtrk.atcommands.ATCommand.AudioCommand;
import com.rtrk.atcommands.ATCommand.FTPCommand;
import com.rtrk.atcommands.ATCommand.FileCommand;
import com.rtrk.atcommands.ATCommand.FileMessageType;
import com.rtrk.atcommands.ATCommand.SMTPCommand;
import com.rtrk.atcommands.ATCommand.STKCommand;

public class M66ProtobufAdapter implements ProtobufAdapter {

	/**
	 * Encode AT command from protobuf format to string format
	 * 
	 * @param protobuf
	 *            - command in protobuf format
	 * @return command in string format
	 */
	@Override
	public String encode(Object protobuf) {

		return null;
	}

	/**
	 * Decode AT command from string format to protobuf format
	 * 
	 * @param command
	 *            - command in string format
	 * @return command in protobuf format
	 */
	@Override
	public Object decode(String command) {
		if (command.startsWith("AT+QFTP")) {
			return decodeFTPCommand(command);
		} else if (command.startsWith("AT+QF")) {
			return decodeFileCommand(command);
		} else if (command.startsWith("AT+QHTTP")) {
			return decodeHTTPCommand(command);
		} else if (command.startsWith("AT+QMM")) {
			return decodeMMSCommand(command);
		} else if (command.startsWith("AT+QAUD")) {
			return decodeAudioCommand(command);
		} else if (command.startsWith("AT+QSMTP")) {
			return decodeSMTPCommand(command);
		} else if (command.startsWith("AT+STK") || command.startsWith("AT+QSTK")) {
			return decodeSTKCommand(command);
		}
		return null;
	}

	private STKCommand decodeSTKCommand(String command) {

		return null;
	}

	private SMTPCommand decodeSMTPCommand(String command) {

		return null;
	}

	private AudioCommand decodeAudioCommand(String command) {

		return null;
	}

	private MMSCommand decodeMMSCommand(String command) {

		return null;
	}

	private FTPCommand decodeFTPCommand(String command) {

		return null;
	}

	/**
	 * Decode File command from string format to File protobuf format
	 * 
	 * @param command
	 *            - File command in string format
	 * @return command in File protobuf format
	 */
	private FileCommand decodeFileCommand(String command) {
		FileCommand.Builder filecommand = FileCommand.newBuilder();
		if (command.startsWith("AT+QFLDS")) {
			filecommand.setMessageType(FileMessageType.GET_STORAGE_DATE_SIZE);
			if ("AT+QFLDS".equals(command)) {
				filecommand.setExecution(true);
			} else if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String namePattern = command.split("=")[1];
				filecommand.setNamePattern(namePattern);
			}
		} else if (command.startsWith("AT+QFLST")) {
			filecommand.setMessageType(FileMessageType.LIST_FILES);
			if ("AT+QFLST".equals(command)) {
				filecommand.setExecution(true);
			} else if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String namePattern = command.split("=")[1];
				filecommand.setNamePattern(namePattern);
			}
		} else if (command.startsWith("AT+QFUPL")) {
			filecommand.setMessageType(FileMessageType.UPLOAD_FILE_TO_STORAGE);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileName = command.split("=")[1].split(",")[0];
				filecommand.setFileName(fileName);
				if (command.split(",").length >= 2) {
					int fileSize = Integer.parseInt(command.split(",")[1]);
					filecommand.setFileSize(fileSize);
				}
				if (command.split(",").length >= 3) {
					int timeout = Integer.parseInt(command.split(",")[2]);
					filecommand.setTimeout(timeout);
				}
				if (command.split(",").length >= 4) {
					int ackMode = Integer.parseInt(command.split(",")[3]);
					filecommand.setAckMode(ackMode);
				}
			}
		} else if (command.startsWith("AT+QFDWL")) {
			filecommand.setMessageType(FileMessageType.DOWNLOAD_FILE_FROM_STORAGE);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileName = command.split("=")[1];
				filecommand.setFileName(fileName);
			}
		} else if (command.startsWith("AT+QFDEL")) {
			filecommand.setMessageType(FileMessageType.DELETE_FILE_IN_STORAGE);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileName = command.split("=")[1];
				filecommand.setFileName(fileName);
			}
		} else if (command.startsWith("AT+QFMOV")) {
			filecommand.setMessageType(FileMessageType.MOVE_FILE);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String srcFileName = command.split("=")[1].split(",")[0];
				String dstFileName = command.split("=")[1].split(",")[1];
				int copy = Integer.parseInt(command.split("=")[1].split(",")[2]);
				int overwarite = Integer.parseInt(command.split("=")[1].split(",")[3]);
				filecommand.setSrcFileName(srcFileName);
				filecommand.setDestFileName(dstFileName);
				filecommand.setCopy(copy);
				filecommand.setOwerwrite(overwarite);
			}
		} else if (command.startsWith("AT+QFOPEN")) {
			filecommand.setMessageType(FileMessageType.OPEN_FILE);
			if (isReadCommand(command)) {
				filecommand.setRead(true);
			} else if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileName = command.split("=")[1].split(",")[0];
				filecommand.setFileName(fileName);
				if (command.split(",").length >= 2) {
					int mode = Integer.parseInt(command.split(",")[1]);
					filecommand.setMode(mode);
				}
				if (command.split(",").length >= 3) {
					int length = Integer.parseInt(command.split(",")[2]);
					filecommand.setLength(length);
				}
			}
		} else if (command.startsWith("AT+QFWRITE")) {
			filecommand.setMessageType(FileMessageType.WRITE_FILE);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileHandle = command.split("=")[1].split(",")[0];
				filecommand.setFileHandle(fileHandle);
				if (command.split(",").length >= 2) {
					int length = Integer.parseInt(command.split(",")[1]);
					filecommand.setLength(length);
				}
				if (command.split(",").length >= 3) {
					int timeout = Integer.parseInt(command.split(",")[2]);
					filecommand.setTimeout(timeout);
				}
			}
		} else if (command.startsWith("AT+QFSEEK")) {
			filecommand.setMessageType(FileMessageType.SEEK_FILE);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileHandle = command.split("=")[1].split(",")[0];
				filecommand.setFileHandle(fileHandle);
				int offset = Integer.parseInt(command.split(",")[1]);
				filecommand.setOffset(offset);
				if (command.split(",").length >= 3) {
					int position = Integer.parseInt(command.split(",")[2]);
					filecommand.setPosition(position);
				}
			}
		} else if (command.startsWith("AT+QFCLOSE")) {
			filecommand.setMessageType(FileMessageType.CLOSE_FILE);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileHandle = command.split("=")[1];
				filecommand.setFileHandle(fileHandle);
			}
		} else if (command.startsWith("AT+QFPOSITION")) {
			filecommand.setMessageType(FileMessageType.GET_OFFSET_OF_THE_FILE_POINTER);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileHandle = command.split("=")[1];
				filecommand.setFileHandle(fileHandle);
			}
		} else if (command.startsWith("AT+QFFLUSH")) {
			filecommand.setMessageType(FileMessageType.FORCE_TO_WRITE_DATA_REMAINING_IN_THE_FILE_BUFFER);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileHandle = command.split("=")[1];
				filecommand.setFileHandle(fileHandle);
			}
		} else if (command.startsWith("AT+QFTUCAT")) {
			filecommand.setMessageType(FileMessageType.TRUNCATE_THE_SPECIFIED_FILE_FROM_FILE_POINTER);
			if (isTestCommand(command)) {
				filecommand.setTest(true);
			} else {
				String fileHandle = command.split("=")[1];
				filecommand.setFileHandle(fileHandle);
			}
		}
		return filecommand.build();
	}

	/**
	 * Decode HTTP command from string format to HTTP protobuf format
	 * 
	 * @param command
	 *            - HTTP command in string format
	 * @return command in HTTP protobuf format
	 */
	private HTTPCommand decodeHTTPCommand(String command) {
		HTTPCommand.Builder httpcommand = HTTPCommand.newBuilder();
		if (command.startsWith("AT+QHTTPDL")) {
			httpcommand.setMessageType(HTTPMessageType.DOWNLODA_FILE_FROM_HTTP_SERVER);
			if (isTestCommand(command)) {
				httpcommand.setTest(true);
			} else {
				String fileName = command.split("=")[1].split(",")[0];
				httpcommand.setFileName(fileName);
				if (command.split(",").length >= 2) {
					int length = Integer.parseInt(command.split("=")[1].split(",")[1]);
					httpcommand.setLength(length);
				}
				if (command.split(",").length >= 3) {
					int waitTime = Integer.parseInt(command.split("=")[1].split(",")[2]);
					httpcommand.setWaitTime(waitTime);
				}
			}
		} else if (command.startsWith("AT+QHTTPURL")) {
			httpcommand.setMessageType(HTTPMessageType.SET_HTTP_SERVER_URL);
			if (isTestCommand(command)) {
				httpcommand.setTest(true);
			} else {
				int urlLength = Integer.parseInt(command.split("=")[1].split(",")[0]);
				int inputTime = Integer.parseInt(command.split("=")[1].split(",")[1]);
				httpcommand.setURLLength(urlLength);
				httpcommand.setInputTime(inputTime);
			}
		} else if (command.startsWith("AT+QHTTPGET")) {
			httpcommand.setMessageType(HTTPMessageType.SEND_HTTP_GET_REQUEST);
			if (isTestCommand(command)) {
				httpcommand.setTest(true);
			} else {
				int inputTime = Integer.parseInt(command.split("=")[1].split(",")[0]);
				httpcommand.setInputTime(inputTime);
			}
		} else if (command.startsWith("AT+QHTTPREAD")) {
			httpcommand.setMessageType(HTTPMessageType.READ_HTTP_SERVER_RESPONSE);
			if (isTestCommand(command)) {
				httpcommand.setTest(true);
			} else {
				int waitTime = Integer.parseInt(command.split("=")[1]);
				httpcommand.setWaitTime(waitTime);
			}
		} else if (command.startsWith("AT+QHTTPPOST")) {
			httpcommand.setMessageType(HTTPMessageType.SEND_HTTP_POST_REQUEST);
			if (isTestCommand(command)) {
				httpcommand.setTest(true);
			} else {
				int bodySize = Integer.parseInt(command.split("=")[1].split(",")[0]);
				int inputTime = Integer.parseInt(command.split("=")[1].split(",")[1]);
				int toReadTime = Integer.parseInt(command.split("=")[1].split(",")[2]);
				httpcommand.setBodySize(bodySize);
				httpcommand.setInputTime(inputTime);
				httpcommand.setToReadTime(toReadTime);
			}
		}
		return httpcommand.build();
	}

	/**
	 * Test if command is test command
	 * 
	 * @param command
	 *            - command in string format
	 * @return true if it's test command or false if it isn't test command
	 */
	private boolean isTestCommand(String command) {
		if ("?".equals(command.split("=")[1])) {
			return true;
		}
		return false;
	}

	/**
	 * Test if command is read command
	 * 
	 * @param command
	 *            - command in string format
	 * @return true if it's read command or false if it isn't read command
	 */
	private boolean isReadCommand(String command) {
		if (command.endsWith("?")) {
			return true;
		}
		return false;
	}

}
