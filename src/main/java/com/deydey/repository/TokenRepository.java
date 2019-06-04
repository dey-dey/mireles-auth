package com.deydey.repository;

import com.deydey.domain.Token;
import org.jdbi.v3.core.Handle;

public interface TokenRepository {
	Token save(Token token);
	Token save(Handle handle, Token token);
}
