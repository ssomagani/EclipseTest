package test;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

import data.Principal;
import data.VoltPrincipalMapper;

public class IndividualInserter extends InserterTest {

	private static String INSERT = "CreatePrincipalOrig";
	private static String SELECT = "PRINCIPALS.select";
	
	VoltPrincipalMapper mapper;
	
	public IndividualInserter() {
		mapper = new VoltPrincipalMapper();
	}
	
	public void insert(int iterCount) {
		IntStream.range(0, iterCount).forEach(
				(iterIndex) -> {
					Principal principal = newPrincipal(0, iterIndex);
					uuids.add(principal.getId());
					Object[] args = {principal.getId().toString(), 
							principal.getKind(), principal.getNote()};
					try {
						client.callProcedure(new InsertCallback(), INSERT, args);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				);
	}
	
	public void verify(UUID id) {
		try {
			client.callProcedure(new SelectCallback(id), SELECT, id.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void checkCallback(ClientResponse resp) {
		if((int) resp.getStatus() != 1) {
			System.out.println("Something wrong with response #");
		} 
	}
	
	private class SelectCallback implements ProcedureCallback {
		private UUID uuid;
		public SelectCallback(UUID uuid) {
			this.uuid = uuid;
		}
		@Override
		public void clientCallback(ClientResponse resp) throws Exception {
			@SuppressWarnings("static-access")
			List<Principal> results = mapper.map(resp.getResults()[0]);
			assert results.size() > 0;
			assert results.get(0).getId().equals(uuid);
		}
	}
	
	public class InsertCallback implements ProcedureCallback {
		@Override
		public void clientCallback(ClientResponse resp) throws Exception {
			checkCallback(resp);
		}
	};
}
