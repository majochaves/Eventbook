function LiveAnchor ( options ) {
	if ( !options.link ) {
		throw new Error( 'LinkAnchor node is required!' );
	}

	if ( !options.anchor ) {
		throw new Error( 'LinkAnchor target is required!' );
	}

	if ( typeof( options.link ) === 'string' ) {
		this.link = document.querySelector( options.link );
	} else {
		this.link = options.link;
	}

	if ( typeof( options.anchor ) === 'string' ) {
		this.anchor = document.querySelector( options.anchor );
	} else {
		this.anchor = options.anchor;
	}

	this.offset = options.offset || 0;
	this.activeClass = options.activeClass || 'active';
	this.link.liveAnchor = this;
	this.anchor.liveAnchor = this;

	this.link.addEventListener( 'click', ( event ) => {
		event.preventDefault();
		let top = $(this.anchor).offset().top - this.offset;
		$( 'html, body' ).stop().animate( { scrollTop: top }, 500, 'swing' );
	});

	if ( !window.globalAnchorObserver ) {
		window.globalAnchorObserver = new IntersectionObserver( function ( entries ) {
			entries.forEach( function ( entry ) {
				if ( entry.isIntersecting ) {
					entry.target.liveAnchor.toggleLink( true );
				} else {
					entry.target.liveAnchor.toggleLink( false );
				}
			});
		});
	}

	globalAnchorObserver.observe( this.anchor );
}

LiveAnchor.prototype.toggleLink = function ( state ) {
	if ( state ) this.link.classList.add( this.activeClass );
	else this.link.classList.remove( this.activeClass );
};
