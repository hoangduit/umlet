package com.baselet.control;


/**
 * temporary separation of constants which are used by NewGridElement class (for an easier migration to a non-awt based gui)
 */
public class NewGridElementConstants {

	public static final int DEFAULT_LINE_THICKNESS = 1;
	
	public static final int RESIZE_TOP = 1, RESIZE_RIGHT = 2, RESIZE_BOTTOM = 4, RESIZE_LEFT = 8, RESIZE_NONE = 0;
	public static final int RESIZE_ALL = RESIZE_TOP | RESIZE_LEFT | RESIZE_BOTTOM | RESIZE_RIGHT;

	public static final int RESIZE_TOP_LEFT = RESIZE_TOP + RESIZE_LEFT;
	public static final int RESIZE_TOP_RIGHT = RESIZE_TOP + RESIZE_RIGHT;
	public static final int RESIZE_BOTTOM_LEFT = RESIZE_BOTTOM + RESIZE_LEFT;
	public static final int RESIZE_BOTTOM_RIGHT = RESIZE_BOTTOM + RESIZE_RIGHT;

	public static boolean show_stickingpolygon = true;

}