/*
 *
 *  * MIT License
 *  *
 *  * Copyright (c) 2018 EPAM Systems
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package com.epam.catgenome.manager;

import com.epam.catgenome.entity.index.Group;
import com.epam.catgenome.entity.index.IndexSearchResult;
import com.epam.catgenome.entity.index.VcfIndexEntry;
import com.epam.catgenome.entity.vcf.VcfFilterForm;
import com.epam.catgenome.entity.vcf.VcfFilterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
//TODO all checks for this class
public class FeatureIndexSecurityService {

    private static final String HAS_ROLE_USER = "hasRole('USER')";
    private static final String READ_PROJECT =
            "hasPermission(#projectId, com.epam.catgenome.entity.project.Project, 'READ')";

    @Autowired
    private FeatureIndexManager featureIndexManager;

    @PreAuthorize(HAS_ROLE_USER)
    public IndexSearchResult searchFeaturesByReference(String featureId, Long referenceId) throws IOException {
        return featureIndexManager.searchFeaturesByReference(featureId, referenceId);
    }

    @PreAuthorize(HAS_ROLE_USER)
    public Set<String> searchGenesInVcfFiles(String search, List<Long> vcfIds) throws IOException {
        return featureIndexManager.searchGenesInVcfFiles(search, vcfIds);
    }

    @PreAuthorize(HAS_ROLE_USER)
    public IndexSearchResult<VcfIndexEntry> filterVariations(VcfFilterForm filterForm) throws IOException {
        return featureIndexManager.filterVariations(filterForm);
    }
    @PreAuthorize(HAS_ROLE_USER)
    public List<Group> groupVariations(VcfFilterForm filterForm, String groupBy) throws IOException {
        return featureIndexManager.groupVariations(filterForm, groupBy);
    }

    @PreAuthorize(READ_PROJECT)
    public IndexSearchResult searchFeaturesInProject(String featureId, Long projectId) throws IOException {
        return featureIndexManager.searchFeaturesInProject(featureId, projectId);
    }

    @PreAuthorize(READ_PROJECT)
    public IndexSearchResult<VcfIndexEntry> filterVariations(VcfFilterForm filterForm, long projectId)
            throws IOException {
        return featureIndexManager.filterVariations(filterForm, projectId);
    }

    @PreAuthorize(READ_PROJECT)
    public List<Group> groupVariations(VcfFilterForm filterForm, long projectId, String groupBy) throws IOException {
        return featureIndexManager.groupVariations(filterForm, projectId, groupBy);
    }

    @PreAuthorize(READ_PROJECT)
    public Set<String> searchGenesInVcfFilesInProject(long projectId, String search, List<Long> vcfIds)
            throws IOException {
        return featureIndexManager.searchGenesInVcfFilesInProject(projectId, search, vcfIds);
    }

    @PreAuthorize(READ_PROJECT)
    public VcfFilterInfo loadVcfFilterInfoForProject(Long projectId) throws IOException {
        return featureIndexManager.loadVcfFilterInfoForProject(projectId);
    }
}
