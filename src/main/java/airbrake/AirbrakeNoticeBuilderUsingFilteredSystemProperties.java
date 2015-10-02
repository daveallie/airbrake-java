// Modified or written by Luca Marrocco for inclusion with airbrake.
// Copyright (c) 2009 Luca Marrocco.
// Licensed under the Apache License, Version 2.0 (the "License")

package airbrake;

import java.util.*;

import org.apache.log4j.*;

public class AirbrakeNoticeBuilderUsingFilteredSystemProperties extends AirbrakeNoticeBuilder {

	public AirbrakeNoticeBuilderUsingFilteredSystemProperties(final String apiKey, final Backtrace backtraceBuilder, final Throwable throwable, final String env, final String appVersion) {
		super(apiKey, backtraceBuilder, throwable, env, appVersion);
		environment(System.getProperties());
		addMDCToSession();
		standardEnvironmentFilters();
		ec2EnvironmentFilters();
	}

	public AirbrakeNoticeBuilderUsingFilteredSystemProperties(final String apiKey, final Backtrace backtraceBuilder, final Throwable throwable, final String url, final String component, final String env, final String appVersion) {
		super(apiKey, backtraceBuilder, throwable, env, appVersion);
		environment(System.getProperties());
		setRequest(url, component);
		addMDCToSession();
		standardEnvironmentFilters();
		ec2EnvironmentFilters();
	}

	private void addMDCToSession() {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = MDC.getContext();

		if (map != null) {
			addSessionKey(":key", Integer.toString(map.hashCode()));
			addSessionKey(":data", map);
		}
	}
}
