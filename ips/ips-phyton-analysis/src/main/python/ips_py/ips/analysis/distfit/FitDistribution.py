# -*- coding: utf-8 -*-
"""
Created on Mon Mar 23 19:37:53 2020

@author: iochord
"""
import warnings
import numpy as np
import scipy.stats as st
from scipy.stats import iqr

class DistributionFitting:
    
    def __init__(self, Data):
        
        self.Data = Data
        
        self.Distributions = [
                
                 st.alpha,st.anglit,st.arcsine,st.beta,st.betaprime]
                             
                # st.bradford,st.burr,st.cauchy,st.chi,st.chi2,st.cosine,
                 
                # st.dgamma,st.dweibull,st.erlang,st.expon,st.exponnorm,
                 
                # st.exponweib,st.exponpow,st.f,st.fatiguelife,st.fisk,
    
                # st.foldcauchy,st.foldnorm,st.frechet_r,st.frechet_l,
                 
                # st.genlogistic,st.genpareto,st.gennorm,st.genexpon,
                
                # st.genextreme,st.gausshyper,st.gamma,st.gengamma,
                 
                # st.genhalflogistic,st.gilbrat,st.gompertz,st.gumbel_r,
    
                # st.gumbel_l,st.halfcauchy,st.halflogistic,st.halfnorm,
                 
                #st.halfgennorm,st.hypsecant,st.invgamma,st.invgauss]
                 
                # st.invweibull,st.johnsonsb,st.johnsonsu,st.ksone,
    
                # st.kstwobign,st.logistic,st.loggamma,st.loggamma,
                 
                # st.loglaplace,st.lognorm,st.lomax,st.maxwell,st.mielke,
                 
                # st.nakagami,st.norm,st.pareto,st.pearson3,st.powerlaw,
    
                # st.powerlognorm,st.powernorm,st.rdist,st.reciprocal,
    
                # st.recipinvgauss,st.semicircular,st.t,st.triang,
    
                # st.truncexpon,st.truncnorm,st.tukeylambda,st.uniform,
    
                # st.vonmises,st.vonmises_line,st.wald,st.weibull_min,
    
                # st.weibull_max,st.wrapcauchy
                 
                 
                 
                 #st.laplace,st.levy,st.levy_l,st.levy_stable,
                 
                 #st.ncx2,st.ncf,st.nct,

    def NumOfBins(self):
    
        self.Data = np.asarray(self.Data)
    
        hat = 2 * iqr(self.Data) / (len(self.Data) ** (1 / 3))

        if hat == 0:
        
            self.bins = int(np.sqrt(len(self.Data)))
        
            return self.bins
        
        else:
            
            self.bins = int(np.ceil((self.Data.max() - self.Data.min()) / hat))
        
            return self.bins
        
    def FitDistribution(self):
        
        y, x = np.histogram(self.Data, bins=self.bins, normed=True)
        
        x = (x + np.roll(x, -1))[:-1] / 2.0

        OptimalDistribution = st.norm.name

        OptimalParams = (0.0, 1.0)

        OptimalSSE = np.inf

        for distribution in self.Distributions:

            try:
                
                with warnings.catch_warnings():
                    
                    warnings.filterwarnings('ignore')
    
                    # fit dist to data
                    params = distribution.fit(self.Data)
    
                    # Separate parts of parameters
                    arg = params[:-2]
                    
                    loc = params[-2]
                    
                    scale = params[-1]
    
                    # Calculate fitted PDF and error with fit in distribution
                    pdf = distribution.pdf(x, loc=loc, scale=scale, *arg)
                    
                    SSE = np.sum(np.power(y - pdf, 2.0))
            
                    # identify if this distribution is better
                    if OptimalSSE > SSE > 0:
                        
                        OptimalDistribution = distribution.name
                        
                        OptimalParams = params
                        
                        OptimalSSE = SSE
    
            except Exception:
                
                pass
        
        return OptimalDistribution, OptimalParams