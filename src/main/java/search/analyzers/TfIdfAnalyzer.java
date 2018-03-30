package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;

public class TfIdfAnalyzer {
    private IDictionary<String, Double> idfScores;
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;
    private IDictionary<URI, Double> normTfId;
    private IDictionary<URI, IDictionary<String, Double>> documentTfScore;
    private IDictionary<String, ISet<Webpage>> termToPage;
    
    public TfIdfAnalyzer(ISet<Webpage> webpages) {
       this.documentTfScore = new ChainedHashDictionary<URI, IDictionary<String, Double>>();
       this.termToPage = new ChainedHashDictionary<String, ISet<Webpage>>();
       for (Webpage page : webpages) {
           IDictionary<String, Double> tfResult = computeTfScores(page.getWords());
           documentTfScore.put(page.getUri(), tfResult);
           for (String term : page.getWords()) {
               
               if (termToPage.containsKey(term)) {
                   termToPage.get(term).add(page);
               } else {
                   ISet<Webpage> set = new ChainedHashSet<Webpage>();
                   set.add(page);
                   termToPage.put(term, set);
               }
           }
       }   
       this.normTfId = new ChainedHashDictionary<URI, Double>();
       this.idfScores = this.computeIdfScores(webpages);
       this.documentTfIdfVectors = computeAllDocumentTfIdfVectors(webpages);
    }

    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
            IDictionary<String, Double> idfResult = new ChainedHashDictionary<String, Double>();
            for (Webpage web : pages) {
                for (String termInPage : web.getWords()) {
                   int denominator = termToPage.get(termInPage).size();   
                   int numerator = pages.size();
                   double result = Math.log(1.0*numerator/denominator);
                   idfResult.put(termInPage, result);
                    
                }
            }
            return idfResult;
    }
    
    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * The input list represents the words contained within a single document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
            double frequency = 0;
            IDictionary<String, Double> result = new ChainedHashDictionary<String, Double>();
        for (String term : words) {
                if (!result.containsKey(term)) {
                    frequency = 1.0 / words.size();
                    result.put(term, frequency);
                } else {
                    frequency = (result.get(term)*words.size() + 1)/words.size();
                    result.put(term, frequency);
                }
        }
        return result;
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
            IDictionary<URI, IDictionary<String, Double>> result = 
                    new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        for (Webpage web: pages) {
            IDictionary<String, Double> resultHelper = new ChainedHashDictionary<String, Double>();
                IDictionary<String, Double> tfScoreTerms = documentTfScore.get(web.getUri());
                double output = 0;
                for (KVPair<String, Double> term : tfScoreTerms) {
                    double tfScoresOfTerm = term.getValue();
                    double idfScoresOfTerm = idfScores.get(term.getKey());
                    double relevance = tfScoresOfTerm * idfScoresOfTerm;
                    resultHelper.put(term.getKey(), relevance);
                    output += relevance*relevance;
                }
                output = Math.sqrt(output);
                normTfId.put(web.getUri(), output);
                result.put(web.getUri(), resultHelper); 
        }
        return result;
    }


     public Double computeRelevance(IList<String> query, URI pageUri) {
        IDictionary<String, Double> documentVector = this.documentTfIdfVectors.get(pageUri);
            IDictionary<String, Double> queryVector = queryVector(query);
            
            double numerator = 0.0;
            double docWordScore = 0.0;
            double queryWordScore = 0.0;
            for (String word : query) {
                if (documentVector.containsKey(word)) {
                    docWordScore = documentVector.get(word);
                } else {
                    docWordScore = 0;
                }
                queryWordScore = queryVector.get(word);
                numerator += queryWordScore*docWordScore;
            }
            double denominator = normTfId.get(pageUri) * norm(queryVector);
            double loop = numerator / denominator;
    
            if (denominator != 0) {
                loop = numerator / denominator;
            } else {
                loop = 0.0;
            }
        return loop;
    }  
    
    private IDictionary<String, Double> queryVector(IList<String> query) {
        IDictionary<String, Double> queryTFScore = computeTfScores(query);
        IDictionary<String, Double> queryVector = new ChainedHashDictionary<String, Double>();
        for (KVPair<String, Double> term : queryTFScore) {
            if (!queryVector.containsKey(term.getKey())) {
                double termtfScore = term.getValue();
                double termIDFScore = this.idfScores.get(term.getKey());
                queryVector.put(term.getKey(), termtfScore*termIDFScore);         
            }
        }
        return queryVector;
    }
    
    private double norm(IDictionary<String, Double> queryVector) {
            double output = 0;
            for (KVPair<String, Double> pair : queryVector) {
                double score = pair.getValue();
                output += score * score;
            }
            return Math.sqrt(output);
    }
}