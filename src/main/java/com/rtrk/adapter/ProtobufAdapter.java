package com.rtrk.adapter;

/**
 * 
 * @author djekanovic
 *
 */

public interface ProtobufAdapter {
	
	/**
	 * Encode AT commands from protobuf format to string format
	 * 
	 * @param protobuf - command in protobuf format
	 * @return command in string format
	 */
	String encode(Object protobuf);

	/**
	 * Decode AT commands from string format to protobuf format
	 * 
	 * @param command - command in string format
	 * @return command in protobuf format
	 */
	Object decode(String command);

}
