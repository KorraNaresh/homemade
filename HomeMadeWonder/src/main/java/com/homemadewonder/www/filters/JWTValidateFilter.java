package com.homemadewonder.www.filters;

//public class JWTValidateFilter extends OncePerRequestFilter{
	
//	
//	 @Override
//	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//	            throws ServletException, IOException {
//	        String jwt = request.getHeader(ApplicationConstants.JWT_HEADER);
//	        if (null != jwt) {
//	            try {
//	                SecretKey key = Keys.hmacShaKeyFor(
//	                		ApplicationConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
//
//	                Claims claims = Jwts.parserBuilder()
//	                        .setSigningKey(key)
//	                        .build()
//	                        .parseClaimsJws(jwt)
//	                        .getBody();
//	                String username = String.valueOf(claims.get("email"));
//	                String authorities = (String) claims.get("authorities");
//	                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
//	                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
//	                SecurityContextHolder.getContext().setAuthentication(auth);
//	            } catch (Exception e) {
//	                throw new BadCredentialsException("Invalid Token received!");
//	            }
//
//	        }
//	        filterChain.doFilter(request, response);
//	    }
//
//	    @Override
//	    protected boolean shouldNotFilter(HttpServletRequest request) {
//	        return request.getServletPath().equals("/user");
//	    }

//}
