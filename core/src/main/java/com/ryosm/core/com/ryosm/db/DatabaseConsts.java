package com.ryosm.core.com.ryosm.db;

public class DatabaseConsts {

	// Database Name
	protected static final String DATABASE_NAME = "ryosm.db";
	// Database Version
	protected static final int DATABASE_VERSION = 1;

	// Tables Names
	protected static final String TABLE_CONFIG = "config";
	protected static final String TABLE_DATA_CACHE = "cache";

	// Field Names for Table TABLE_CONFIG
	protected static final String CONFIG_KEY = "key";
	protected static final String CONFIG_VALUE = "value";
	
	// Columns for Table TABLE_CONFIG
	protected static final String CONFIG_KEY_ENDPOINT = "timeout";

	// Columns for TABLE_DATA_CACHE
	protected static final String DATA_CACHE_KEY = "key";
	protected static final String DATA_CACHE_OBJECT = "object";
	protected static final String DATA_CACHE_TIMESTAMP = "timestamp";
	
}
