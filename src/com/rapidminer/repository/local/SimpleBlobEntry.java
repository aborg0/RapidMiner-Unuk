/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2012 by Rapid-I and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapid-i.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.repository.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.rapidminer.repository.BlobEntry;
import com.rapidminer.repository.Folder;
import com.rapidminer.repository.RepositoryException;

/**
 * Reference on BLOB entries in the repository.
 * 
 * @author Simon Fischer
 */
public class SimpleBlobEntry extends SimpleDataEntry implements BlobEntry {

	private static final String BLOB_SUFFIX = ".blob";

	SimpleBlobEntry(String name, SimpleFolder containingFolder,	LocalRepository localRepository) {
		super(name, containingFolder, localRepository);
	}

	private File getFile() {
		return new File(((SimpleFolder)getContainingFolder()).getFile(), getName()+BLOB_SUFFIX);
	}
	
	@Override
	public long getDate() {
		return getFile().lastModified();
	}

	@Override
	public long getSize() {
		return getFile().length();
	}

	@Override
	public void delete() throws RepositoryException {
		getFile().delete();
		super.delete();
	}
	
	@Override
	public boolean rename(String newName) {
		renameFile(getFile(), newName);
		return super.rename(newName);
	}
	
	@Override
	public boolean move(Folder newParent) {	
		moveFile(getFile(), ((SimpleFolder)newParent).getFile());
		return super.move(newParent);
	}
	
	@Override
	public boolean move(Folder newParent, String newName) {	
		moveFile(getFile(), ((SimpleFolder)newParent).getFile(), newName, BLOB_SUFFIX);
		return super.move(newParent, newName);
	}

	@Override
	public String getType() {
		return BlobEntry.TYPE_NAME;
	}

	@Override
	public String getMimeType() {
		return getProperty("mimetype");
	}

	@Override
	public InputStream openInputStream() throws RepositoryException {
		try {
			return new FileInputStream(getFile());
		} catch (FileNotFoundException e) {
			throw new RepositoryException("Cannot open stream from '"+getFile()+"': "+e, e);
		}
	}

	@Override
	public OutputStream openOutputStream(String mimeType) throws RepositoryException {
		putProperty("mimetype", mimeType);
		try {
			return new FileOutputStream(getFile());			
		} catch (IOException e) {
			throw new RepositoryException("Cannot open stream from '"+getFile()+"': "+e, e);
		}
	}
}
