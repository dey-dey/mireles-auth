package com.deydey.iam.domain.identity.token;

import com.deydey.iam.domain.identity.authentication.Token;

public interface TokenRepository {
	Token save(Token token);
}
