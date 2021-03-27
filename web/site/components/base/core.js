/**
 * Wrapper to eliminate json errors
 * @param {string} str - JSON string
 * @returns {object} - parsed or empty object
 */
function parseJSON ( str ) {
	try {
		if ( str )  return JSON.parse( str );
		else return {};
	} catch ( error ) {
		//{DEL DIST BUILDER}
		console.warn( error );
		//{DEL}
		return {};
	}
}

/**
 * Component readiness check
 * @param {string} name
 * @return {Promise}
 */
function checkComponent ( name ) {
	return new Promise( function ( resolve ) {
		let component = window.components[ name ];
		if ( !component || component.state === 'absent' ) {
			resolve();
		} else {
			if ( component.state === 'ready' ) {
				resolve();
			} else {
				window.addEventListener( `${name}:readyScripts`, resolve );
			}
		}
	});
}

/**
 * @param {Array} params - parameters array
 * @param {function} cb - function that returns a promise
 * @returns {Promise}
 */
function makeAsync ( params, cb ) {
	let inclusions = [];

	params.forEach( function ( path ) {
		inclusions.push( cb( path ) );
	});

	return Promise.all( inclusions );
}

/**
 * @param {Array} params - parameters array
 * @param {function} cb - function that returns a promise
 * @returns {Promise}
 */
function makeSync ( params, cb ) {
	let chain = Promise.resolve();

	params.forEach( function( path ) {
		chain = chain.then( cb.bind( null, path ) );
	});

	return chain;
}

/**
 * Adds a link tag to the page.
 * @param {string} path - path to styles file
 */
function includeStyles ( path ) {
	return new Promise( function ( resolve ) {
		if ( document.querySelector( `link[href="${path}"]` ) ) {
			resolve();
		} else {
			let link = document.createElement( 'link' );
			link.setAttribute( 'rel', 'stylesheet' );
			link.setAttribute( 'href', path );
			link.addEventListener( 'load', resolve );
			document.querySelector( 'head' ).appendChild( link );
		}
	});
}

/**
 * Adds a script tag to the page.
 * @param {Array|string} path - path to script file
 * @return {Promise}
 */
function includeScript ( path ) {
	return new Promise( function ( resolve ) {
		let node = document.querySelector( `script[src="${path}"]` );

		if ( node ) {
			if ( node.getAttribute( 'data-loaded' ) === 'true' ) {
				resolve();
			} else {
				node.addEventListener( 'load', resolve );
			}
		} else {
			let script = document.createElement( 'script' );
			script.src = path;

			script.addEventListener( 'load', function () {
				script.setAttribute( 'data-loaded', 'true' );
				resolve();
			});

			document.querySelector( 'head' ).appendChild( script );
		}
	});
}

/**
 * @param {object} component
 */
function initComponent( component ) {
	let
		stylesState = Promise.resolve(),
		scriptsState = Promise.resolve();

	//{DEL DIST BUILDER}
	console.log( `%c[${component.name}] init:`, 'color: lightgreen; font-weight: 900;', component.nodes.length );
	//{DEL}
	component.state = 'load';

	if ( component.styles ) {
		stylesState = stylesState.then( makeAsync.bind( null, component.styles, includeStyles ) );
	}

	if ( component.script ) {
		scriptsState = scriptsState.then( makeSync.bind( null, component.script, includeScript ) );
	}

	if ( component.dependencies ) {
		scriptsState = scriptsState.then( makeSync.bind( null, component.dependencies, checkComponent ) );
	}

	if ( component.init ) {
		scriptsState = scriptsState.then( component.init.bind( null, component.nodes ) );
	}

	stylesState.then( function () {
		window.dispatchEvent( new Event( `${component.name}:readyStyles` ) );
	});

	scriptsState.then( function () {
		window.dispatchEvent( new Event( `${component.name}:readyScripts` ) );
		component.state = 'ready';
	});

	return {
		scriptsState: scriptsState,
		stylesState: stylesState
	};
}

/**
 * @param {Element} [node] - element in which components will be searched
 */
function initComponents( node ) {
	let
		stylesPromises = [],
		scriptsPromises = [];

	if ( !window.components ) throw new Error( 'window.components is not defined' );
	components = window.components;
	node = node || document;

	// Components preparation
	for ( let key in components ) {
		let component = components[ key ];

		component.name = key;
		component.nodes = Array.from( node.querySelectorAll( component.selector ) );

		if ( node.constructor.name === 'HTMLElement' && node.matches( component.selector ) ) {
			component.nodes.unshift( node );
		}

		if ( component.styles && !(component.styles instanceof Array) ) {
			component.styles = [ component.styles ];
		}

		if ( component.script && !(component.script instanceof Array) ) {
			component.script = [ component.script ];
		}

		if ( component.dependencies && !(component.dependencies instanceof Array) ) {
			component.dependencies = [ component.dependencies ];
		}

		if ( component.nodes.length ) {
			component.state = 'pending';
		} else {
			component.state = 'absent';
		}
	}

	for ( let key in components ) {
		let component = components[ key ];
		if ( component.state === 'pending' ) {
			let componentPromises = initComponent( component );
			stylesPromises.push( componentPromises.stylesState );
			scriptsPromises.push( componentPromises.scriptsState );
		}
	}

	Promise.all( scriptsPromises ).then( function () {
		window.dispatchEvent( new Event( 'components:ready' ) );
	});

	Promise.all( stylesPromises ).then( function () {
		window.dispatchEvent( new Event( 'components:stylesReady' ) );
	});
}


// DOM Mutation Observer to track new components
const observer = new MutationObserver( function( mutationsList, observer ) {
	for ( let mutation of mutationsList ) {
		if ( mutation.type === 'childList' && mutation.addedNodes.length ) {
			mutation.addedNodes.forEach( function ( node ) {
				if ( node.nodeType === Node.ELEMENT_NODE ) {
					initComponents( node );
				}
			});
		}
	}
});


// Main
window.addEventListener( 'load', function () {
	initComponents();

	observer.observe( document, {
		childList: true,
		subtree: true
	});
});
