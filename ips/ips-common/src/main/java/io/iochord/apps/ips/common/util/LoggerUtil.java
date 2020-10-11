package io.iochord.apps.ips.common.util;

import java.util.logging.Logger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class LoggerUtil {

	public static void logError(Exception ex) {
		Logger.getGlobal().severe(ex.getMessage());
		ex.printStackTrace();
	}

	public static void logInfo(String info) {
		Logger.getGlobal().info(info);
	}
	
}