package com.netcracker.rest.service;

import com.netcracker.rest.model.Agreement;

public interface AgreementService {
	public abstract String save(Agreement agree);
	public abstract Agreement read(String file);
}
