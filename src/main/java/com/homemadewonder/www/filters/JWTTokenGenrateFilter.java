package com.homemadewonder.www.filters;

//public class JWTTokenGenrateFilter extends OncePerRequestFilter {

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (null != authentication) {
//			SecretKey key = Keys.hmacShaKeyFor(ApplicationConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
//			String jwt = Jwts.builder().setIssuer("homemadewonder").setSubject("JWT Token")
//					.claim("email", authentication.getName())
//					.claim("authorities", populateAuthorities(authentication.getAuthorities())).setIssuedAt(new Date())
//					.setExpiration(new Date((new Date()).getTime() + 30000000)).signWith(key).compact();
//			response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
//		}
//
//		filterChain.doFilter(request, response);
//	}
//
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) {
//		return !request.getServletPath().equals("/user");
//	}
//
//	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
//		Set<String> authoritesSet = new HashSet<>();
//		for (GrantedAuthority authority : collection) {
//			authoritesSet.add(authority.getAuthority());
//		}
//		return String.join(",", authoritesSet);
//	}

//}
