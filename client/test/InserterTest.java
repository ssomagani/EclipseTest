package test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.voltdb.InProcessVoltDBServer;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;
import org.voltdb.client.ProcedureCallback;

import data.Principal;

public abstract class InserterTest {

	private static int TOTAL_COUNT = 1000000;
	protected static Client client;
	protected static int PART_COUNT;
	
	public static void main (String[] args) throws UnknownHostException, IOException, ProcCallException {
		InProcessVoltDBServer volt = new InProcessVoltDBServer();
		volt.start();

        volt.runDDLFromPath("./ddl.sql");

        client = volt.getClient();
		
		PART_COUNT = getPartitionCount();
		
		IndividualInserter indivInserter = new IndividualInserter();
		indivInserter.insert(TOTAL_COUNT);
		
		try {
			client.drain();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		uuids.forEach((x) -> indivInserter.verify(x));
		
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static final List<UUID> uuids = Collections.synchronizedList(new ArrayList<UUID>());
	
	protected Principal newPrincipal(int batchIndex, int iterIndex) {
		Principal principal = new Principal();
		principal.setKind("USER");
		principal.setNote("Some note for " + principal.getKind() + ", principal #"+ iterIndex);
		uuids.add(principal.getId());
		return principal;
	}
	
	protected static int getPartitionCount() throws NoConnectionsException, IOException, ProcCallException {
		VoltTable result = client.callProcedure("@Statistics", "PARTITIONCOUNT", 0).getResults()[0];
		int partCount = -1;
		if(result.advanceRow()) {
			partCount = (int) result.getLong("PARTITION_COUNT");
		}
		return partCount;
	}
}
