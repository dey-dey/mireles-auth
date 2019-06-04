package com.deydey.repository;

import com.deydey.domain.Company;
import org.jdbi.v3.core.Handle;

public interface CompanyRepository {
	Company save(Company company);
	Company save(Handle handle, Company company);
}
