package data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.voltdb.VoltTable;
import org.voltdb.VoltType;

public class VoltPrincipalMapper {
	// field to column defns
	// TODO: use reflection
	private final static String ID_COLUMN = "id";
	private final static String KIND_COLUMN = "kind";
	private final static String NOTE_COLUMN = "note";
	// more
	
	private static VoltTable.ColumnInfo[] COLUMNS = {
		new VoltTable.ColumnInfo(ID_COLUMN, VoltType.STRING),
		new VoltTable.ColumnInfo(KIND_COLUMN, VoltType.STRING),
		new VoltTable.ColumnInfo(NOTE_COLUMN, VoltType.STRING),
		//
	};
   

	public VoltPrincipalMapper() {}
	
	public static List<Principal> map(VoltTable voltTable) {
		List<Principal> principals = new ArrayList<Principal>();
		
		while (voltTable.advanceRow()) {
			principals.add(mapRow(voltTable));
		}
		
		return principals;
	}
	
	private static Principal mapRow(VoltTable voltTable) {
		Principal principal = new Principal();
		
		principal.setId(UUID.fromString((String) voltTable.get(ID_COLUMN, VoltType.STRING)));
		principal.setKind((String) voltTable.get(KIND_COLUMN, VoltType.STRING));
		principal.setNote((String) voltTable.get(NOTE_COLUMN, VoltType.STRING));
		// ...
		
		return principal;		
	}
	
	public static VoltTable map(Principal principal) {
		VoltTable voltTable = new VoltTable(COLUMNS);
		addPrincipal(voltTable, principal);
		return voltTable;
	}
	
	/**
	 * Map a list of principals to a new VoltTable
	 * @param principals list of principals to map
	 * @return dynamically created VoltTable containing principals in original order
	 */
	public static VoltTable map(List<Principal> principals) {
		// make dynamic table
		VoltTable voltTable = new VoltTable(COLUMNS);
		
		// fill with data, each principal is a new row
		for (Principal principal : principals) {
			addPrincipal(voltTable, principal);
		}
		
		return voltTable;
	}
	
	// add a single principal to VoltTable instance
	private static void addPrincipal(VoltTable voltTable, Principal principal) {
		// tbd, find way to not use positions here 
		voltTable.addRow(
			principal.getId().toString(),
			principal.getKind(),
			principal.getNote());
	}
}
