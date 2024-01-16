import { useEffect, useState } from 'react';
import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import Tippy from '@tippyjs/react/headless';

import { Wrapper as PropperWrapper } from '~/components/Popper';
import styles from './Header.module.scss';
import logo from '~/assets/images/logo-tma.webp';
import InvoiceItem from '~/components/InvoiceItem';

const cx = classNames.bind(styles);

function Header() {
    const [searchResult, setSearchResult] = useState([]);
    useEffect(() => {
        setTimeout(() => {
            // setSearchResult([1, 2]);
        }, 0);
    });

    return (
        <header className={cx('wrapper')}>
            <div className={cx('inner')}>
                <div className={cx('logo')}>
                    <img src={logo} alt="logo-tma" />
                    <div className={cx('title')}>Invoice Tool</div>
                </div>

                <Tippy
                    interactive={true}
                    visible={searchResult.length > 0}
                    render={(attrs) => (
                        <div className={cx('search-result')} tabIndex="-1" {...attrs}>
                            <PropperWrapper>
                                <h4 className={cx('search-title')}>Invoices</h4>
                                <InvoiceItem></InvoiceItem>
                                <InvoiceItem></InvoiceItem>
                                <InvoiceItem></InvoiceItem>
                                <InvoiceItem></InvoiceItem>
                            </PropperWrapper>
                        </div>
                    )}
                >
                    <div className={cx('search')}>
                        <input placeholder="Search" spellCheck={false} />
                        <button className={cx('search-btn')}>
                            <FontAwesomeIcon icon={faMagnifyingGlass} />
                        </button>
                    </div>
                </Tippy>

                <div className={cx('actions')}></div>
            </div>
        </header>
    );
}

export default Header;
