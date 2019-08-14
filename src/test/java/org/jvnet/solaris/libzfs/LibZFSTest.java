/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at usr/src/OPENSOLARIS.LICENSE
 * or http://www.opensolaris.org/os/licensing.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at usr/src/OPENSOLARIS.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */
package org.jvnet.solaris.libzfs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple ZFS-aware App.
 *
 * @author Kohsuke Kawaguchi
 * @author Jim Klimov
 */
public class LibZFSTest {
	private static final String ZFS_TEST_POOL_BASENAME_DEFAULT = "test_zfs";

	private String ZFS_TEST_DATASET_MEDIA_NAME = "media";

	private LibZFS zfs = null;
	
	@Before
	public void setup() {
		if (zfs == null) {
			zfs = new LibZFS();
		}
	}

	@After
	public void tearDown() {
		zfs.dispose();
	}
	
	/* Note: here and below we assume, validly for Solarish systems

	 * 
	 * (global zones at least), that an /rpool exists and is mountable */
	@Test
	public void testGetFilesystemTree() {

		// List<ZFSPool> pools = zfs.roots();
		// if ( pools.size() > 0 ) {
		// ZFSObject filesystem = pools.get(0);
		ZFSObject filesystem = zfs.open(ZFS_TEST_POOL_BASENAME_DEFAULT +'/' + ZFS_TEST_DATASET_MEDIA_NAME);
		if (filesystem != null) {
			System.out.println("single tree: " + filesystem.getName());
			for (ZFSObject child : filesystem.children()) {
				if (child instanceof ZFSSnapshot) {
					System.out.println("snapshot  :" + child.getName());
				} else {
					System.out.println("child     :" + child.getName());
				}
			}
		} else {
			System.out.println("no zfs pools were found");
		}
	}

	@Test
	public void testGetFilesFromMedia( ) {
		ZFSObject filesystem = zfs.open(ZFS_TEST_POOL_BASENAME_DEFAULT +'/' + ZFS_TEST_DATASET_MEDIA_NAME);

		filesystem.children().get(0).descendants().forEach(System.out::print);
	}

}
