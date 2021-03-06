// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.db;

import jodd.db.jtx.DbJtxTransactionManager;
import jodd.db.pool.CoreConnectionPool;
import jodd.log.LoggerFactory;
import org.junit.After;
import org.junit.Before;

public abstract class DbH2TestCase {

	protected DbJtxTransactionManager dbtxm;
	protected CoreConnectionPool cp;

	@Before
	public void setUp() throws Exception {
		LoggerFactory.setLoggerFactory(new TestLoggerFactory());
		if (dbtxm != null) {
			return;
		}
		cp = new CoreConnectionPool();
		cp.setDriver("org.h2.Driver");
		cp.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

		cp.setUser("sa");
		cp.setPassword("");
		cp.init();
		dbtxm = new DbJtxTransactionManager(cp);
	}

	@After
	public void tearDown() throws Exception {
		dbtxm.close();
		cp.close();
		dbtxm = null;
	}

	// ---------------------------------------------------------------- helpers

	protected int executeUpdate(DbSession session, String s) {
		return new DbQuery(session, s).autoClose().executeUpdate();
	}

	protected void executeUpdate(String sql) {
		new DbQuery(sql).autoClose().executeUpdate();
	}

	protected void executeCount(DbSession session, String s) {
		new DbQuery(session, s).autoClose().executeCount();
	}

}

