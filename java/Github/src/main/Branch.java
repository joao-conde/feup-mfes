package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Branch {
	public String name;
	public Boolean isProtected;
	private VDMSeq commits = SeqUtil.seq();

	public void cg_init_Branch_1(final String n, final Boolean prot) {

		name = n;
		isProtected = prot;
		return;

	}

	public Branch(final String n, final Boolean prot) {

		cg_init_Branch_1(n, prot);
	}

	public void commit(final Commit c) {

		commits = SeqUtil.conc(Utils.copy(commits), SeqUtil.seq(c));

	}

	public void mergeBranch(final Branch branch) {

		VDMSeq seqCompResult_1 = SeqUtil.seq();
		VDMSeq set_2 = branch.getCommits();
		for (Iterator iterator_2 = set_2.iterator(); iterator_2.hasNext();) {
			Commit c = ((Commit) iterator_2.next());
			Boolean forAllExpResult_2 = true;
			VDMSeq set_3 = Utils.copy(commits);
			for (Iterator iterator_3 = set_3.iterator(); iterator_3.hasNext() && forAllExpResult_2;) {
				Commit co = ((Commit) iterator_3.next());
				forAllExpResult_2 = !(Utils.equals(co.hash, c.hash));

			}
			if (forAllExpResult_2) {
				seqCompResult_1.add(c);
			}

		}
		VDMSeq all_commits = SeqUtil.conc(Utils.copy(commits), Utils.copy(seqCompResult_1));
		VDMSeq sorted_commits = Utils.copy(all_commits);
		long toVar_2 = 1L;
		long byVar_1 = -1L;
		for (Long i = (long) all_commits.size(); byVar_1 < 0 ? i >= toVar_2 : i <= toVar_2; i += byVar_1) {
			long toVar_1 = i.longValue() - 1L;

			for (Long j = 1L; j <= toVar_1; j++) {
				if (new Date.x(((Commit) Utils.get(sorted_commits, j)).timestamp).after(new Date.x(
						((Commit) Utils.get(sorted_commits, j.longValue() + 1L)).timestamp))) {
					Commit temp = ((Commit) Utils.get(sorted_commits, j));
					Utils.mapSeqUpdate(sorted_commits, j, ((Commit) Utils.get(sorted_commits, j.longValue() + 1L)));
					Utils.mapSeqUpdate(sorted_commits, j.longValue() + 1L, temp);
				}
			}
		}
		commits = Utils.copy(sorted_commits);

	}

	public VDMSeq getCommits() {

		return Utils.copy(commits);

	}

	public Branch() {

	}

	public String toString() {

		return "Branch{" + "name := " + Utils.toString(name) + ", isProtected := " + Utils.toString(isProtected)
				+ ", commits := " + Utils.toString(commits) + "}"

		;

	}

}